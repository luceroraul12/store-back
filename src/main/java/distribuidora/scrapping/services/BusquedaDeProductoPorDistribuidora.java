package distribuidora.scrapping.services;


import distribuidora.scrapping.entities.DonGasparEntidad;
import distribuidora.scrapping.entities.FacundoEntidad;
import distribuidora.scrapping.entities.IndiasEntidad;
import distribuidora.scrapping.entities.LaGranjaDelCentroEntidad;
import distribuidora.scrapping.entities.MelarEntidad;
import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.entities.SudamerikEntidad;
import distribuidora.scrapping.entities.UnionEntidad;
import distribuidora.scrapping.enums.Distribuidora;
import distribuidora.scrapping.repositories.UnionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @param <Entidad>
 * @param <Auxiliar>
 * @see DonGasparEntidad
 * @see FacundoEntidad
 * @see IndiasEntidad
 * @see LaGranjaDelCentroEntidad
 * @see MelarEntidad
 * @see SudamerikEntidad
 *
 * @see UnionRepository
 */
public abstract class BusquedaDeProductoPorDistribuidora<Entidad, Auxiliar>  implements  RecoleccionDeInformacionInterface<Entidad>{

    protected Distribuidora distribuidora;

    @Autowired
    UnionRepository<Entidad> unionRepository;


    /**
     * Metodo por el cual se inicia el proceso de busqueda de datos en el proceso especifico
     * @param elementoAuxiliar Clase del elemento que tiene los elementos especificos necesarios
     * @return lista de productos en la entidad seleccionada
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
     * @see DonGasparEntidad
     * @see FacundoEntidad
     * @see IndiasEntidad
     * @see LaGranjaDelCentroEntidad
     * @see MelarEntidad
     * @see SudamerikEntidad
     * @see Distribuidora
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
     * @see BusquedaDeProductoPorDistribuidora#almacenarProductosEnBaseDeDatos(List)
     */
    public void actualizarProductosEnBaseDeDatos(List<Entidad> productos){
        unionRepository.deleteUnionEntidadByDistribuidora(distribuidora);
        almacenarProductosEnBaseDeDatos(productos);
    }

    @Override
    public List<Producto> convertirTodosAProducto(List<Entidad> productosEntidad){
        List<Producto> productosFinales = new ArrayList<>();

        productosEntidad.forEach(
                productoEntidad -> productosFinales.add(
                        mapearEntidadaProducto(productoEntidad)
                )
        );
        return productosFinales;
    }
}
