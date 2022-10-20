package distribuidora.scrapping.util;

import distribuidora.scrapping.entities.productos.especificos.DonGasparEntidad;
import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.enums.Distribuidora;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class DonGasparUtil extends ProductoUtil<DonGasparEntidad> {

    @Override
    public List<Producto> convertirProductoyDevolverlo(DonGasparEntidad productoSinConvertir) {
        return Collections.singletonList(Producto.builder()
                .descripcion(productoSinConvertir.getNombreProducto())
                .precioPorCantidadEspecifica(productoSinConvertir.getPrecio())
                .distribuidora(Distribuidora.DON_GASPAR)
                .build());
    }
}
