package distribuidora.scrapping.services;

import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.entities.UnionEntidad;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Encargada de las actividades de conversion hacia Producto.
 * @param <Entidad>
 * @see Producto
 */
public abstract class MapeadorDeProducto<Entidad> {

    /**
     * Converierte un producto de entidad especifica a Producto.
     * Tiene que ser implementado por las clases especificas / finales.
     * @param productoEntidad no debe ser null
     * @return Producto
     */
    protected abstract Producto mapearEntidadaProducto(Entidad productoEntidad);

    /**
     * convierte un conjunto de productos en Entidad especifica a conjunto de Productos.
     * Deberia usar el metodo unitario para llevar a cabo esta actividad.
     * @param productosEntidad de cierta entidad especifica
     * @see UnionEntidad
     * @return lista de productos
     * @see MapeadorDeProducto#mapearEntidadaProducto(Entidad productoEntidad)
     */
    protected List<Producto> convertirTodosAProducto(@NotNull List<Entidad> productosEntidad){
        List<Producto> productosFinales = new ArrayList<>();
        productosEntidad.forEach(
                pEntidad -> productosFinales.add(
                        mapearEntidadaProducto(pEntidad)
                )
        );
        return productosFinales;
    };
}
