package distribuidora.scrapping.services;


import distribuidora.scrapping.entities.ProductoEspecifico;
import distribuidora.scrapping.entities.UnionEntidad;
import distribuidora.scrapping.enums.Distribuidora;
import distribuidora.scrapping.enums.TipoDistribuidora;
import distribuidora.scrapping.repositories.UnionRepository;
import distribuidora.scrapping.services.excel.BusquedorPorExcel;
import distribuidora.scrapping.services.webscrapping.BusquedorPorWebScrapping;
import org.springframework.beans.factory.annotation.Autowired;

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
public abstract class BuscadorDeProductosEntidad<Entidad extends ProductoEspecifico, Auxiliar>  extends RelacionadorConProducto<Entidad> {

    /**
     * Cada servicio final tiene que tener la enumeracion de la distribuidora a la que pertenece.
     */
    protected Distribuidora distribuidora;
    protected TipoDistribuidora tipoDistribuidora;
    protected LocalDate fechaUltimaActualizacion;


    @Autowired
    UnionRepository<Entidad> unionRepository;


    /**
     * Metodo por el cual se inicia el proceso de busqueda de datos en el proceso especifico
     * @param elementoAuxiliar Clase del elemento que tiene los elementos especificos necesarios
     * @return lista de productos en la entidad seleccionada
     * @see BusquedorPorExcel
     * @see BusquedorPorWebScrapping
     */
    protected abstract List<Entidad> trabajarDocumentoyObtenerSusProductosEspecificos(Auxiliar elementoAuxiliar);


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
}
