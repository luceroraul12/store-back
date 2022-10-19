package distribuidora.scrapping.services;

import distribuidora.scrapping.entities.Producto;

import java.util.List;

/**
 * Indica las actividades de conversion de cierta entidad a Producto.
 * @param <Entidad>
 * @see Producto
 */
public interface RecoleccionDeInformacionInterface<Entidad> {

    /**
     * Para llaver un producto de entidad especifica a Producto
     * @param productoEntidad no debe ser null
     * @return Producto
     */
    Producto mapearEntidadaProducto(Entidad productoEntidad);

    /**
     * convierte un conjunto de productos en Entidad especifica a conjunto de Productos.
     * Deberia usar el metodo unitario para llevar a cabo esta actividad.
     * @param productosEntidad
     * @return
     * @see RecoleccionDeInformacionInterface#mapearEntidadaProducto(Entidad productoEntidad) 
     */
    List<Producto> convertirTodosAProducto(List<Entidad> productosEntidad);

}
