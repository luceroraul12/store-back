package distribuidora.scrapping.entities.productos.especificos;

import distribuidora.scrapping.entities.ProductoEspecifico;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LaGranjaDelCentroEntidad extends ProductoEspecifico {
    private final String nombreProducto;
    private final Double precio;

    @Builder
	public LaGranjaDelCentroEntidad(String id, String distribuidoraCodigo, String nombreProducto, Double precio) {
		super(id, distribuidoraCodigo);
		this.nombreProducto = nombreProducto;
		this.precio = precio;
	}

	@Override
	public Double getPrecioExterno() {
		// TODO Auto-generated method stub
		return precio;
	}
}
