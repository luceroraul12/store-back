package distribuidora.scrapping.services;


import distribuidora.scrapping.entities.DatosDistribuidora;
import distribuidora.scrapping.entities.ProductoEspecifico;
import distribuidora.scrapping.enums.Distribuidora;
import distribuidora.scrapping.enums.TipoDistribuidora;
import distribuidora.scrapping.services.excel.BusquedorPorExcel;
import distribuidora.scrapping.services.webscrapping.BusquedorPorWebScrapping;
import distribuidora.scrapping.util.ProductoUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * Clase padre de todos los servicios de tipos de busqueda.
 * Contiene metodos sobre la base de datos y el abstract para una entidadEspecifica.
 * @param <Entidad> necesario para saber el tipo de distribuidora
 * @param <Auxiliar> clase con los datos necesarios para poder comenzar busqueda
 */
@Data
public abstract class BuscadorDeProductos<Entidad extends ProductoEspecifico, Auxiliar>
{

    /**
     * Es la enumercacion que identifica a cada implementacion de este servicio.
     * Toda implementacion utilizada debe tenerlo seteado.
     */
    private Distribuidora distribuidora;
    /**
     * Es el identificador del tipo de busqueda.
     * Toda implementacion utilizada debe tenerlo seteado.
     */
    private TipoDistribuidora tipoDistribuidora;

    private LocalDate fechaUltimaActualizacion;

    /**
     * Componente utilitario para realizar conversiones.
     */
    @Autowired
    private ProductoUtil<Entidad> productoUtil;

    /**
     * Servicio basado en Productos finales.
     */
    @Autowired
    private ProductoServicio productoServicio;

    /**
     * Servicio que hace referencia a las diferentes implementaciones para productos especificos.
     */
    @Autowired
    private ProductoEspecificoServicio<Entidad> productoEspecificoServicio;

    @Autowired
    private DatoDistribuidoraServicio datoDistribuidoraServicio;

    /**
     * Metodo post constructor para poder setear atributos de cada clase.
     */
    @PostConstruct
    private void init(){
        initTipoBusqueda();
        initImplementacion();
        verificarExistenciaEnBaseDeDatosEspecifica();
    }

    /**
     * Metodo iniciador para el tipo de busqueda
     */
    protected abstract void initTipoBusqueda();

    /**
     * Metodo iniciador para la implementacion.
     */
    protected abstract void initImplementacion();

    /**
     * Metodo pre destruccion del bean.<br>
     * De momento no hay nada asignado en el, pero esta.
     */
    @PreDestroy
    protected abstract void destroy();

    /**
     * Metodo por el cual se inicia el proceso de busqueda de datos en el proceso especifico.
     * Luego de la adquisicion de datos, la misma se guarda en  en las colecciones correspondientes.
     * @param elementoAuxiliar Clase del elemento que tiene los elementos especificos necesarios
     * @see BusquedorPorExcel
     * @see BusquedorPorWebScrapping
     * @see BuscadorDeProductos#adquirirProductosEntidad(Auxiliar elementoAuxiliar)
     * @see BuscadorDeProductos#actualizarProductosEnTodasLasColecciones(List productos)
     */
    public void generarProductosEntidadYActualizarCollecciones(Auxiliar elementoAuxiliar){
        try {
            actualizarProductosEnTodasLasColecciones(
                    adquirirProductosEntidad(elementoAuxiliar)
            );
        } catch (Exception e) {
            System.out.println("error en actualizar productos en todas las colecciones");;
        }
    }

    /**
     * Metodo a implementar por cada clase de tipo de busqueda.
     * Este metodo permite unicamente la adquisicion de productos de cierta entidad y solo eso.
     * @param elementoAuxiliar
     * @return lista de productos
     */
    protected abstract List<Entidad> adquirirProductosEntidad(Auxiliar elementoAuxiliar);;


    /**
     * Elimina los datos almacenados de cierta distribuidora y vuelve a guardar con datos nuevos.
     * Esto se realiza en la coleccion Entidad Especifica como en la de Productos
     * @param productos de ciertan entidad
//     * @see BuscadorDeProductos#almacenarProductosEspecificos(List)
     */
    public void actualizarProductosEnTodasLasColecciones(List<Entidad> productos){
        try {
            this.productoServicio.actualizarProductosPorDistribuidora(
                    productoUtil.arregloToProducto(productos),
                    this.distribuidora
            );
        } catch (Exception e) {
            System.out.println("error al actualizar productos Finales");
        }
        try {
            this.datoDistribuidoraServicio.actualizarDatos(
                    Collections.singletonList(DatosDistribuidora.builder()
                            .distribuidora(getDistribuidora())
                            .fechaActualizacion(LocalDate.now().toString())
                            .tipo(getTipoDistribuidora())
                            .cantidadDeProductosAlmacenados(productos.size())
                            .build())
            );
        } catch (Exception e) {
            System.out.println("error al actualizar datos de distribuidora");
        }
        try {
            this.productoEspecificoServicio.actualizarDatos(productos);
        } catch (Exception e) {
            System.out.println("error al guardar productos especificos");
        }
    }

    /**
     * Inicializacion encargada de verificar Base de datos.<br>
     * Verifica si existe la implementacion en la base de datos, en caso de no existir, crea una con los datos de la misma.<br>
     * Toda implementacion debe tener seteado:
     * {@link BuscadorDeProductos#distribuidora},{@link BuscadorDeProductos#tipoDistribuidora}
     * para poder realizar esta verificacion.
     */
    private void verificarExistenciaEnBaseDeDatosEspecifica() {
        if (!this.datoDistribuidoraServicio.existsByDistribuidora(getDistribuidora())){
            System.out.println(this.distribuidora +" no existe, creando ...");

            this.datoDistribuidoraServicio.actualizarDatos(
                    Collections.singletonList(DatosDistribuidora.builder()
                            .distribuidora(getDistribuidora())
                            .tipo(getTipoDistribuidora())
                            .cantidadDeProductosAlmacenados(0)
                            .fechaActualizacion(LocalDate.now().toString())
                            .build())
            );
        };
    }

}
