package distribuidora.scrapping.entities.productos.especificos;

import distribuidora.scrapping.entities.ProductoEspecifico;
import distribuidora.scrapping.enums.Distribuidora;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DonGasparEntidad extends ProductoEspecifico {
    String nombreProducto;
    Double precio;

    @Builder
    public DonGasparEntidad(Distribuidora distribuidora, String nombreProducto, Double precio) {
        super(distribuidora);
        this.nombreProducto = nombreProducto;
        this.precio = precio;
    }
}
