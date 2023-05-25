package distribuidora.scrapping.entities.productos.especificos;

import distribuidora.scrapping.entities.ProductoEspecifico;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FacundoEntidad extends ProductoEspecifico {
    private final String categoria;

    private final String categoriaRenglon;

    private final String subcategoria;
    private final String cantidad;
    private final Double precioMayor;
    private final Double precioMenor;
    
    @Builder
	public FacundoEntidad(String id, String distribuidoraCodigo, String categoria, String categoriaRenglon,
			String subcategoria, String cantidad, Double precioMayor, Double precioMenor) {
		super(id, distribuidoraCodigo);
		this.categoria = categoria;
		this.categoriaRenglon = categoriaRenglon;
		this.subcategoria = subcategoria;
		this.cantidad = cantidad;
		this.precioMayor = precioMayor;
		this.precioMenor = precioMenor;
	}

	@Override
	public Double getPrecioExterno() {
		// TODO Auto-generated method stub
		return precioMayor;
	}
    
}
