package distribuidora.scrapping.entities.productos.especificos;


import distribuidora.scrapping.entities.ProductoEspecifico;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class SudamerikEntidad extends ProductoEspecifico {
    private String nombreProducto;
    private String cantidadEspecifca;
    private Double precio;

}
