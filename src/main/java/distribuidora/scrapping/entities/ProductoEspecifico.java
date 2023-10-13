package distribuidora.scrapping.entities;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase padre de cualquier producto Entidad diferente de Producto.
 * Tiene la finalidad de unificar todas los productos de Entidades Especificas y que se trabaje en funcion a esta.
 * Cada clase especifica a cada distribuidora debe heredar de esta clase.
 */

@Getter
@Setter
public abstract class ProductoEspecifico {
    @Id
    private String id;
    private String distribuidora;

    public ProductoEspecifico(String id, String distribuidora) {
		super();
		this.id = id;
		this.distribuidora = distribuidora;
	}

	public abstract Double getPrecioExterno();
    
    
}
