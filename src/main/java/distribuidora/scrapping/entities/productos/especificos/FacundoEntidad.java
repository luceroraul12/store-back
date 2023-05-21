package distribuidora.scrapping.entities.productos.especificos;

import distribuidora.scrapping.entities.ProductoEspecifico;
import distribuidora.scrapping.enums.Distribuidora;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Getter
public class FacundoEntidad extends ProductoEspecifico {
    private String categoria;

    private String categoriaRenglon;

    private String subcategoria;
    private String cantidad;
    private Double precioMayor;
    private Double precioMenor;
    
    @Builder
	public FacundoEntidad(String id, Distribuidora distribuidora, String categoria, String categoriaRenglon,
			String subcategoria, String cantidad, Double precioMayor, Double precioMenor) {
		super(id, distribuidora);
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
