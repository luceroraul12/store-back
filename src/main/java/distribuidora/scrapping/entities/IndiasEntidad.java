package distribuidora.scrapping.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IndiasEntidad {
    private String      rubro;
    private Integer     codigo;
    private String      descripcion;
    private Double      precio;
}
