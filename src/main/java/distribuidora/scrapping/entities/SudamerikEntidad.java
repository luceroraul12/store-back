package distribuidora.scrapping.entities;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class SudamerikEntidad {
    private String nombreProducto;
    private String cantidadEspecifca;
    private Double precio;

}
