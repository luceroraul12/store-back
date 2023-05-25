package distribuidora.scrapping.entities.productos.especificos;

import distribuidora.scrapping.entities.ProductoEspecifico;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DonGasparEntidad extends ProductoEspecifico {
    String nombreProducto;
    Double precio;

    @Builder
	public DonGasparEntidad(String id, String distribuidoraCodigo, String nombreProducto, Double precio) {
		super(id, distribuidoraCodigo);
		this.nombreProducto = nombreProducto;
		this.precio = precio;
	}

	@Override
	public Double getPrecioExterno() {
		return precio;
	}
}
