package distribuidora.scrapping.util;

import distribuidora.scrapping.entities.MelarEntidad;
import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.enums.Distribuidora;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class MelarUtil extends ProductoUtil<MelarEntidad>{
    @Override
    Collection<Producto> convertirProductoyDevolverlo(MelarEntidad productoSinConvertir) {
        Collection<Producto> productosCreados = new ArrayList<>();

        /*
        Quiero tener un descripcion en el siguiente orden
        1.producto
        2.origen
        3.cantidad
        4.precio
         */

        String descripcionFraccion = String.format(
                "%s - %s X %s%s",
                productoSinConvertir.getProducto(),
                productoSinConvertir.getOrigen(),
                productoSinConvertir.getFraccion(),
                productoSinConvertir.getMedida()
        );

        String descripcionGranel = String.format(
                "%s - %s X %s%s",
                productoSinConvertir.getProducto(),
                productoSinConvertir.getOrigen(),
                productoSinConvertir.getGranel(),
                productoSinConvertir.getMedida()
        );

        productosCreados.add(
                Producto
                        .builder()
                        .descripcion(descripcionFraccion)
                        .precioPorCantidadEspecifica(productoSinConvertir.getPrecioFraccionado())
                        .distribuidora(Distribuidora.MERLAR)
                        .build()
        );
        productosCreados.add(
                Producto
                        .builder()
                        .descripcion(descripcionGranel)
                        .precioPorCantidadEspecifica(productoSinConvertir.getPrecioGranel())
                        .distribuidora(Distribuidora.MERLAR)
                        .build()
        );

        return productosCreados;
    }
}
