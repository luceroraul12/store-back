package distribuidora.scrapping.entities.productos.especificos;

import distribuidora.scrapping.entities.ProductoEspecifico;
import lombok.Builder;
import lombok.Getter;

@Getter
public class IndiasEntidad extends ProductoEspecifico {
    private final String      rubro;
    private final Integer     codigo;
    private final String      descripcion;
    private final Double      precio;
    
    @Builder
	public IndiasEntidad(String id, String distribuidoraCodigo, String rubro, Integer codigo, String descripcion,
			Double precio) {
		super(id, distribuidoraCodigo);
		this.rubro = rubro;
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.precio = precio;
	}

	@Override
	public Double getPrecioExterno() {
		return precio;
	}
    
}
