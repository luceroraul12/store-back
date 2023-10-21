package distribuidora.scrapping.util;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.ExternalProduct;
import distribuidora.scrapping.entities.LookupValor;
import distribuidora.scrapping.entities.productos.especificos.LaGranjaDelCentroEntidad;

@Component
public class LaGranjaDelCentroUtil extends ProductoUtil<LaGranjaDelCentroEntidad>{
    @Override
    public List<ExternalProduct> convertirProductoyDevolverlo(LaGranjaDelCentroEntidad productoSinConvertir) {
        return Collections.singletonList(ExternalProduct.builder()
                .title(productoSinConvertir.getNombreProducto())
                .price(productoSinConvertir.getPrecio())
                .code(productoSinConvertir.getId())
                .distribuidora(new LookupValor(Constantes.LV_DISTRIBUIDORA_LA_GRANJA_DEL_CENTRO))
                .build());
    }
}
