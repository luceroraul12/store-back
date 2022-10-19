package distribuidora.scrapping.entities.productos.especificos;

import distribuidora.scrapping.entities.ProductoEspecifico;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class DonGasparEntidad extends ProductoEspecifico {
    String nombreProducto;
    Double precio;
}
