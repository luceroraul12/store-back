package distribuidora.scrapping.entities.productos.especificos;

import distribuidora.scrapping.entities.ProductoEspecifico;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IndiasEntidad extends ProductoEspecifico {
    private String      rubro;
    private Integer     codigo;
    private String      descripcion;
    private Double      precio;
}
