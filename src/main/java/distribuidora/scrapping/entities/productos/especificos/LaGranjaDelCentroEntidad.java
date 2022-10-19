package distribuidora.scrapping.entities.productos.especificos;

import distribuidora.scrapping.entities.ProductoEspecifico;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class LaGranjaDelCentroEntidad extends ProductoEspecifico {
    private String nombreProducto;
    private Double precio;
}
