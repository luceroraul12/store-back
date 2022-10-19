package distribuidora.scrapping.util;

import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.entities.productos.especificos.SudamerikEntidad;
import distribuidora.scrapping.enums.Distribuidora;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class SudamerikUtil extends ProductoUtil<SudamerikEntidad> {
    @Override
    List<Producto> convertirProductoyDevolverlo(SudamerikEntidad productoSinConvertir) {
        String cantidadEspecifica = productoSinConvertir.getCantidadEspecifca();
        String nombreProducto = productoSinConvertir.getNombreProducto();

        String[] arregloCantidad = cantidadEspecifica.split(" ");
        String descripcion = "";

        boolean estaContenido = false;

        for (String parte : arregloCantidad){
            if (nombreProducto.contains(parte)){
                descripcion = nombreProducto;
                estaContenido = true;
                break;
            }
        }
        if (!estaContenido){
            /*
            Para este caso, en caso de no tenerlo, simplemente concateno ambos strings y nada mas.
             */
            descripcion = String.format(
                    "%s X %s",
                    nombreProducto,
                    cantidadEspecifica
            );
        }

        return Collections.singletonList(Producto
                .builder()
                .descripcion(descripcion)
                .precioPorCantidadEspecifica(productoSinConvertir.getPrecio())
                .distribuidora(Distribuidora.SUDAMERIK)
                .build());
    }
}
