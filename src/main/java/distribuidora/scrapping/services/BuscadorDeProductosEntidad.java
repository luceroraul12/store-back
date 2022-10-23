package distribuidora.scrapping.services;


import distribuidora.scrapping.entities.ProductoEspecifico;
import distribuidora.scrapping.entities.UnionEntidad;
import distribuidora.scrapping.enums.Distribuidora;
import distribuidora.scrapping.enums.TipoDistribuidora;
import distribuidora.scrapping.repositories.UnionRepository;
import distribuidora.scrapping.services.excel.BusquedorPorExcel;
import distribuidora.scrapping.services.webscrapping.BusquedorPorWebScrapping;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDate;
import java.util.List;

/**
 * Clase padre de todos los servicios de tipos de busqueda.
 * Contiene metodos sobre la base de datos y el abstract para una entidadEspecifica.
 * @param <Entidad> necesario para saber el tipo de distribuidora
 * @param <Auxiliar> clase con los datos necesarios para poder comenzar busqueda
 * @see UnionEntidad
 * @see UnionRepository
 */
@Data
public abstract class BuscadorDeProductosEntidad<Entidad extends ProductoEspecifico, Auxiliar>  extends RelacionadorConProducto<Entidad> {

    /**
     * Cada servicio final tiene que tener la enumeracion de la distribuidora a la que pertenece.
     */
    private Distribuidora distribuidora;
    /**
     * Cada tipo de busqueda tiene que tener la enumeracion del tipo a la que pertenece
     */
    private TipoDistribuidora tipoDistribuidora;

    private LocalDate fechaUltimaActualizacion;


    @Autowired
    private UnionRepository<Entidad> unionRepository;


    /**
     * Es el metodo de inicializacion para los tipos de busqueda.<br>
     * Se debe tener seteado {@link BuscadorDeProductosEntidad#tipoDistribuidora}
     * @see BuscadorDeProductosEntidad#setTipoDistribuidora(TipoDistribuidora) 
     */
    @Order(1)
    @PostConstruct
    protected abstract void initTipoDeBusqueda();

    /**
     * Es el metodo de inicializacion para las implementaciones.<br>
     * Se debe tener seteado {@link BuscadorDeProductosEntidad#distribuidora}
     * @see BuscadorDeProductosEntidad#setTipoDistribuidora(TipoDistribuidora)
     */
    @Order(2)
    @PostConstruct
    protected abstract void initEspecifico();

    @PreDestroy
    protected abstract void destroy();

    /**
     * Metodo por el cual se inicia el proceso de busqueda de datos en el proceso especifico.
     * Luego de la adquisicion de datos, la misma se guarda en  en las colecciones correspondientes.
     * @param elementoAuxiliar Clase del elemento que tiene los elementos especificos necesarios
     * @see BusquedorPorExcel
     * @see BusquedorPorWebScrapping
     * @see BuscadorDeProductosEntidad#adquirirProductosEntidad(Auxiliar elementoAuxiliar)
     * @see BuscadorDeProductosEntidad#actualizarProductosEnTodasLasColecciones(List productos)
     */
    public void generarProductosEntidadYActualizarCollecciones(Auxiliar elementoAuxiliar){
        actualizarProductosEnTodasLasColecciones(
                adquirirProductosEntidad(elementoAuxiliar)
        );
    }

    /**
     * Metodo a implementar por cada clase de tipo de busqued.
     * Este metodo permite unicamente la adquisicion de productos de cierta entidad y solo eso.
     * @param elementoAuxiliar
     * @return lista de productos
     */
    protected abstract List<Entidad> adquirirProductosEntidad(Auxiliar elementoAuxiliar);;





    /**
     * Obtiene todos los productos almacenados en la base de datos en funcion a la distribuidora
     */
    public UnionEntidad<Entidad> obtenerProductosEspecificos(){
        return unionRepository.findByDistribuidora(distribuidora);
    };


    /**
     * Almacena productos en la base de datos
     * @param productos Productos en su entidad correspondiente
     * @see UnionEntidad
     */
    protected void almacenarProductosEspecificos(List<Entidad> productos) {
        UnionEntidad<Entidad> unionEntidad = new UnionEntidad<>();
        unionEntidad.setDatos(
                productos
        );
        unionEntidad.setDistribuidora(distribuidora);
        unionEntidad.setTipoDistribuidora(tipoDistribuidora);
        unionEntidad.setFechaScrap(LocalDate.now());
        unionRepository.save(unionEntidad);
    }

    /**
     * Elimina los datos almacenados de cierta distribuidora y vuelve a guardar con datos nuevos.
     * Esto se realiza en la coleccion Entidad Especifica como en la de Productos
     * @param productos de ciertan entidad
     * @see BuscadorDeProductosEntidad#almacenarProductosEspecificos(List)
     * @see UnionEntidad
     */
    public void actualizarProductosEnTodasLasColecciones(List<Entidad> productos){
        unionRepository.deleteUnionEntidadByDistribuidora(distribuidora);
        almacenarProductosEspecificos(productos);

        actualizarProductosFinalesPorDistribuidora(
                convertirTodosAProducto(productos),
                distribuidora
        );
    }

    /**
     * Inicializacion encargada de verificar Base de datos.<br>
     * Verifica si existe la implementacion en la base de datos, en caso de no existir, crea una con los datos de la misma.<br>
     * Toda implementacion debe tener seteado:
     * {@link BuscadorDeProductosEntidad#distribuidora},{@link BuscadorDeProductosEntidad#tipoDistribuidora}
     * para poder realizar esta verificacion.
     */
    @Order(3)
    @PostConstruct
    private void verificarExistenciaEnBaseDeDatosEspecifica() {
        if (!this.unionRepository.existsByDistribuidora(this.distribuidora)){
            System.out.println(this.distribuidora +"no existe, creando ...");
            UnionEntidad<Entidad> union = new UnionEntidad<>();
            union.setDistribuidora(this.distribuidora);
            union.setTipoDistribuidora(this.tipoDistribuidora);
            union.setFechaScrap(LocalDate.now());
            this.unionRepository.save(union);
        };
    }

}
