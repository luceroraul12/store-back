package distribuidora.scrapping.entities.productos.especificos;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class DonGasparEntidad {
    String nombreProducto;
    Double precio;
}
