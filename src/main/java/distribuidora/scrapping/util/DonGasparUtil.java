package distribuidora.scrapping.util;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.productos.especificos.DonGasparEntidad;
import distribuidora.scrapping.entities.Producto;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class DonGasparUtil extends ProductoUtil<DonGasparEntidad> {

    @Override
    public List<Producto> convertirProductoyDevolverlo(DonGasparEntidad productoSinConvertir) {
        return Collections.singletonList(Producto.builder()
                .descripcion(productoSinConvertir.getNombreProducto())
                .precioPorCantidadEspecifica(productoSinConvertir.getPrecio())
                .distribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_DON_GASPAR)
                .build());
    }
}
