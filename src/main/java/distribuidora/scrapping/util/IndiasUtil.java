package distribuidora.scrapping.util;

import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.entities.productos.especificos.IndiasEntidad;
import distribuidora.scrapping.enums.Distribuidora;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class IndiasUtil extends ProductoUtil<IndiasEntidad>{
    @Override
    public List<Producto> convertirProductoyDevolverlo(IndiasEntidad productoEntidad) {
        Distribuidora distribuidora = Distribuidora.INDIAS;
        String descripcion = String.format(
                "%s",
                productoEntidad.getDescripcion()
        );
        return Collections.singletonList(Producto.builder()
                .descripcion(descripcion)
                .precioPorCantidadEspecifica(productoEntidad.getPrecio())
                .distribuidora(distribuidora)
                .build());
    }
}
