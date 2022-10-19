package distribuidora.scrapping.services;

import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.entities.UnionEntidad;
import distribuidora.scrapping.enums.Distribuidora;
import distribuidora.scrapping.repositories.ProductoRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Encargada de las actividades de conversion hacia Producto.
 * @param <Entidad>
 * @see Producto
 */
public abstract class RelacionadorConProducto<Entidad> {

    @Autowired
    ProductoServicio productoServicio;

    /**
     * Converierte un producto de entidad especifica a Producto.
     * Tiene que ser implementado por las clases especificas / finales.
     * @param productoEntidad no debe ser null
     * @return Producto
     */
    protected abstract List<Producto> mapearEntidadaProducto(Entidad productoEntidad);

    /**
     * convierte un conjunto de productos en Entidad especifica a conjunto de Productos.
     * Deberia usar el metodo unitario para llevar a cabo esta actividad.
     * @param productosEntidad de cierta entidad especifica
     * @see UnionEntidad
     * @return lista de productos
     * @see RelacionadorConProducto#mapearEntidadaProducto(Entidad productoEntidad)
     */
    protected List<Producto> convertirTodosAProducto(@NotNull List<Entidad> productosEntidad){
        List<Producto> productosFinales = new ArrayList<>();
        productosEntidad.forEach(
                pEntidad -> productosFinales.addAll(
                        mapearEntidadaProducto(pEntidad)
                )
        );
        return productosFinales;
    };

    /**
     * Encargado de almacenar datos en la colleccion de Producto para cierta distribuidora.
     * @param productos productos a guardar.
     * @param distribuidora distribuidora a utilizar.
     * @see ProductoServicio
     * @see ProductoRepository
     */
    public void actualizarProductosFinalesPorDistribuidora(List<Producto> productos, Distribuidora distribuidora) {
        productoServicio.actualizarProductosPorDistribuidora(productos, distribuidora);
    }

    /**
     * Encargado de obtener todos los productos que se encuentran almacenados en la coleccion de producto
     * @return lista de productos.
     */
    public List<Producto> obtenerTodosLosProductosFinalesAlmacenados() {
        return productoServicio.obtenerTodosLosProductosAlmacenados();
    }
}
