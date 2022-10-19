package distribuidora.scrapping.util;

import distribuidora.scrapping.entities.productos.especificos.LaGranjaDelCentroEntidad;
import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.enums.Distribuidora;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
public class LaGranjaDelCentroUtil extends ProductoUtil<LaGranjaDelCentroEntidad>{
    @Override
    Collection<Producto> convertirProductoyDevolverlo(LaGranjaDelCentroEntidad productoSinConvertir) {
        return Collections.singleton(Producto.builder()
                .descripcion(productoSinConvertir.getNombreProducto())
                .precioPorCantidadEspecifica(productoSinConvertir.getPrecio())
                .distribuidora(Distribuidora.LA_GRANJA_DEL_CENTRO)
                .build());
    }
}
