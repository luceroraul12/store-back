package distribuidora.scrapping.util;

import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.entities.productos.especificos.FacundoEntidad;
import distribuidora.scrapping.enums.Distribuidora;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FacundoUtil extends ProductoUtil<FacundoEntidad> {
    @Override
    public List<Producto> convertirProductoyDevolverlo(FacundoEntidad productoEntidad) {
        Distribuidora distribuidora = Distribuidora.FACUNDO;
        List<Producto> productosGenerados = new ArrayList<>();
        String descripcionMenor = String.format(
                "%s %s X %s X menor",
                productoEntidad.getCategoria(),
                productoEntidad.getSubcategoria(),
                productoEntidad.getCantidad()
        );
        String descripcionMayor = String.format(
                "%s %s X %s X mayor",
                productoEntidad.getCategoria(),
                productoEntidad.getSubcategoria(),
                productoEntidad.getCantidad()
        );

        productosGenerados.add(
                Producto.builder()
                        .descripcion(descripcionMayor)
                        .precioPorCantidadEspecifica(productoEntidad.getPrecioMayor())
                        .distribuidora(distribuidora)
                        .build()
        );
        productosGenerados.add(
                Producto.builder()
                        .descripcion(descripcionMenor)
                        .precioPorCantidadEspecifica(productoEntidad.getPrecioMenor())
                        .distribuidora(distribuidora)
                        .build()
        );

        return productosGenerados;
    }
}
