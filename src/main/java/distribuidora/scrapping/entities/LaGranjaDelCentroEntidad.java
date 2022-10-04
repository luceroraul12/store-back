package distribuidora.scrapping.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class LaGranjaDelCentroEntidad {
    private String nombreProducto;
    private Double precio;
}
