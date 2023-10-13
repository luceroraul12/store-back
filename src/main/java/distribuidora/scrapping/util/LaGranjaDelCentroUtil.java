package distribuidora.scrapping.util;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.entities.productos.especificos.LaGranjaDelCentroEntidad;

@Component
public class LaGranjaDelCentroUtil extends ProductoUtil<LaGranjaDelCentroEntidad>{
    @Override
    public List<Producto> convertirProductoyDevolverlo(LaGranjaDelCentroEntidad productoSinConvertir) {
        return Collections.singletonList(Producto.builder()
                .descripcion(productoSinConvertir.getNombreProducto())
                .precioPorCantidadEspecifica(productoSinConvertir.getPrecio())
                .distribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_LA_GRANJA_DEL_CENTRO)
                .build());
    }
}
