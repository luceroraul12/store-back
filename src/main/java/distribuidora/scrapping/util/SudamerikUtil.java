package distribuidora.scrapping.util;

import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.entities.productos.especificos.SudamerikEntidad;
import distribuidora.scrapping.entities.productos.especificos.SudamerikEntidad.SudamerikConjuntoEspecifico;
import distribuidora.scrapping.enums.Distribuidora;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class SudamerikUtil extends ProductoUtil<SudamerikEntidad> {
    @Override
    public List<Producto> convertirProductoyDevolverlo(SudamerikEntidad productoSinConvertir) {
        List<Producto> productosConvertidos = new ArrayList<>();
        String nombreProducto = productoSinConvertir.getNombreProducto();
        List<SudamerikConjuntoEspecifico> conjuntoEspecificos = productoSinConvertir.getCantidadesEspecificas();

        conjuntoEspecificos.forEach(
                conjunto -> {
                    productosConvertidos.add(
                            Producto
                                    .builder()
                                    .descripcion(
                                        validaryAgregarPropiedadesQueNoEstanRepetidasEnOriginal(
                                                nombreProducto,
                                                Collections.singletonList(conjunto.getCantidadEspecifica())
                                                )
                                    )
                                    .precioPorCantidadEspecifica(
                                            conjunto.getPrecio()
                                    )
                                    .build()
                    );
                }
        );
        return productosConvertidos;
    }
}
