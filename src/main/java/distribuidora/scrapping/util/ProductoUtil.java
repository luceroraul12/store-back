package distribuidora.scrapping.util;

import distribuidora.scrapping.entities.Producto;

import java.util.ArrayList;
import java.util.Collection;

public abstract class ProductoUtil<Entidad> {

    public Collection<Producto> arregloToProducto(Collection<Entidad> productos){
        Collection<Producto> productosConvertidos = new ArrayList<>();

        productos.forEach(p -> {
            productosConvertidos.addAll(
                    convertirProductoyDevolverlo(p)
            );
        });

        return productosConvertidos;
    }

    /**
     * ingresa un producto y salen todas las conversiones necesarias
     * @param productoSinConvertir
     * @return puede que un unico producto tenga diferentes ofertas precios en funcion a la cantidad
     */
    abstract Collection<Producto> convertirProductoyDevolverlo(Entidad productoSinConvertir);




}
