package distribuidora.scrapping.services.internal;

import java.util.List;

import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.entities.dto.ProductoInternoDto;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.entities.ProductoEspecifico;
import distribuidora.scrapping.entities.ProductoInterno;

@Service
public interface InventorySystem {

	/**
	 * pide a la base la lista de productos especificos y la interna para luego realizar la actualizacion de precios y
	 * actualizar la base de datos
	 * @return numero de productos que fueron actualizado sus precios
	 * @see #actualizarPrecioConProductosEspecificos(List, List)
	 */
	int actualizarPreciosAutomatico();

	/**
	 * Metodo logico que realiza la modificacion sobre los productos en memoria pasados por referencia
	 * @param especificos lista de productos de diferentes distribuidoras
	 * @param internos lista de productos de la tienda actual
	 * @see #actualizarPreciosAutomatico()
	 */
	void actualizarPrecioConProductosEspecificos(List<Producto> especificos, List<ProductoInterno> internos);

	Integer crearProductos(List<ProductoInternoDto> dtos);
}
