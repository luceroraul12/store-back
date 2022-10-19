package distribuidora.scrapping.services;


import distribuidora.scrapping.entities.UnionEntidad;
import distribuidora.scrapping.enums.Distribuidora;
import distribuidora.scrapping.repositories.UnionRepository;
import distribuidora.scrapping.services.excel.BusquedorPorExcel;
import distribuidora.scrapping.services.webscrapping.BusquedorPorWebScrapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

/**
 * Clase padre de todos los tipos de busqueda.
 * Contiene metodos sobre la base de datos y el abstract para buscar.
 * @param <Entidad> necesario para saber el tipo de distribuidora
 * @param <Auxiliar> clase con los datos necesarios para poder comenzar busqueda
 * @see UnionEntidad
 * @see UnionRepository
 */
public abstract class BuscadorDeProductos<Entidad, Auxiliar>  extends MapeadorDeProducto<Entidad> {

    /**
     * Cada servicio final tiene que tener la enumeracion de la distribuidora a la que pertenece.
     */
    protected Distribuidora distribuidora;

    @Autowired
    UnionRepository<Entidad> unionRepository;


    /**
     * Metodo por el cual se inicia el proceso de busqueda de datos en el proceso especifico
     * @param elementoAuxiliar Clase del elemento que tiene los elementos especificos necesarios
     * @return lista de productos en la entidad seleccionada
     * @see BusquedorPorExcel
     * @see BusquedorPorWebScrapping
     */
    protected abstract List<Entidad> trabajarDocumentoyObtenerSusProductos(Auxiliar elementoAuxiliar);


    /**
     * Obtiene todos los productos almacenados en la base de datos en funcion a la distribuidora
     */
    public UnionEntidad<Entidad> obtenerProductos(){
        return unionRepository.findByDistribuidora(distribuidora);
    };


    /**
     * Almacena productos en la base de datos
     * @param productos Productos en su entidad correspondiente
     * @see UnionEntidad
     */
    protected void almacenarProductosEnBaseDeDatos(List<Entidad> productos) {
        UnionEntidad<Entidad> unionEntidad = new UnionEntidad<>();
        unionEntidad.setDatos(
                productos
        );
        unionEntidad.setDistribuidora(distribuidora);
        unionEntidad.setFechaScrap(LocalDate.now());
        unionRepository.save(unionEntidad);
    }

    /**
     * Elimina los datos almacenados de cierta distribuidora y vuelve a guardar con datos que deben ser nuevos
     * @param productos
     * @see BuscadorDeProductos#almacenarProductosEnBaseDeDatos(List)
     */
    public void actualizarProductosEnBaseDeDatos(List<Entidad> productos){
        unionRepository.deleteUnionEntidadByDistribuidora(distribuidora);
        almacenarProductosEnBaseDeDatos(productos);
    }
}
