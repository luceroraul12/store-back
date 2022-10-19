package distribuidora.scrapping.util;

import distribuidora.scrapping.entities.productos.especificos.LaGranjaDelCentroEntidad;
import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.enums.Distribuidora;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class LaGranjaDelCentroUtil extends ProductoUtil<LaGranjaDelCentroEntidad>{
    @Override
    List<Producto> convertirProductoyDevolverlo(LaGranjaDelCentroEntidad productoSinConvertir) {
        return Collections.singletonList(Producto.builder()
                .descripcion(productoSinConvertir.getNombreProducto())
                .precioPorCantidadEspecifica(productoSinConvertir.getPrecio())
                .distribuidora(Distribuidora.LA_GRANJA_DEL_CENTRO)
                .build());
    }
}
