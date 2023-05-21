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
	public DonGasparEntidad(String id, Distribuidora distribuidora, String nombreProducto, Double precio) {
		super(id, distribuidora);
		this.nombreProducto = nombreProducto;
		this.precio = precio;
	}

	@Override
	public Double getPrecioExterno() {
		return precio;
	}
}
