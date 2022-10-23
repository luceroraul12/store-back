package distribuidora.scrapping.entities.productos.especificos;

import distribuidora.scrapping.entities.ProductoEspecifico;
import distribuidora.scrapping.enums.Distribuidora;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LaGranjaDelCentroEntidad extends ProductoEspecifico {
    private String nombreProducto;
    private Double precio;

    @Builder
    public LaGranjaDelCentroEntidad(Distribuidora distribuidora, String nombreProducto, Double precio) {
        super(distribuidora);
        this.nombreProducto = nombreProducto;
        this.precio = precio;
    }
}
