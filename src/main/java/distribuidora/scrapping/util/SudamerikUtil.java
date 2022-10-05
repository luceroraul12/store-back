package distribuidora.scrapping.util;

import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.entities.SudamerikEntidad;

import java.util.Collection;

public class SudamerikUtil extends ProductoUtil<SudamerikEntidad> {
    @Override
    Collection<Producto> convertirProductoyDevolverlo(SudamerikEntidad productoSinConvertir) {
        return null;
    }
}
