package distribuidora.scrapping.util;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.ExternalProduct;
import distribuidora.scrapping.entities.LookupValor;
import distribuidora.scrapping.entities.productos.especificos.DonGasparEntidad;

@Component
public class DonGasparUtil extends ProductoUtil<DonGasparEntidad> {

    @Override
    public List<ExternalProduct> convertirProductoyDevolverlo(DonGasparEntidad productoSinConvertir) {
        return Collections.singletonList(ExternalProduct.builder()
                .title(productoSinConvertir.getNombreProducto())
                .price(productoSinConvertir.getPrecio())
                .distribuidora(new LookupValor(Constantes.LV_DISTRIBUIDORA_DON_GASPAR))
                .build());
    }
}
