package distribuidora.scrapping.util;

import distribuidora.scrapping.entities.DonGasparEntidad;
import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.enums.Distribuidora;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
public class DonGasparUtil extends ProductoUtil<DonGasparEntidad> {

    @Override
    Collection<Producto> convertirProductoyDevolverlo(DonGasparEntidad productoSinConvertir) {
        return Collections.singleton(
                Producto.builder()
                        .descripcion(productoSinConvertir.getNombreProducto())
                        .precioPorCantidadEspecifica(productoSinConvertir.getPrecio())
                        .distribuidora(Distribuidora.DON_GASPAR)
                        .build()
        );
    }
}
