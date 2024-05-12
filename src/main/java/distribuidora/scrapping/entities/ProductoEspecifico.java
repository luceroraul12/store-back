package distribuidora.scrapping.entities;

import lombok.Data;

/**
 * Clase padre de cualquier producto Entidad diferente de Producto. Tiene la
 * finalidad de unificar todas los productos de Entidades Especificas y que se
 * trabaje en funcion a esta. Cada clase especifica a cada distribuidora debe
 * heredar de esta clase.
 */

@Data
public abstract class ProductoEspecifico {
	private String id;
	private String distribuidora;

	public ProductoEspecifico(String id, String distribuidora) {
		super();
		this.id = id;
		this.distribuidora = distribuidora;
	}

	public abstract Double getPrecioExterno();

}
