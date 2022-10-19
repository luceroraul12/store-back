package distribuidora.scrapping.entities.productos.especificos;

import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Data
@Builder
public class FacundoEntidad {
    private String categoria;
    private String subcategoria;
    private String cantidad;
    private Double precioMayor;
    private Double precioMenor;
}
