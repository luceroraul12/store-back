package distribuidora.scrapping.services.internal;

import java.util.List;

import org.springframework.stereotype.Service;

import distribuidora.scrapping.dto.CategoryDto;
import distribuidora.scrapping.dto.DatosDistribuidoraDto;
import distribuidora.scrapping.dto.ProductCustomerDto;
import distribuidora.scrapping.dto.ProductoInternoDto;
import distribuidora.scrapping.entities.ProductoInterno;

@Service
public interface InventorySystem {

	/**
	 * pide a la base la lista de productos especificos y la interna para luego
	 * realizar la actualizacion de precios y actualizar la base de datos
	 * 
	 * @return numero de productos que fueron actualizado sus precios
	 * @see #actualizarPrecioConProductosEspecificos(List, List)
	 */
	int actualizarPreciosAutomatico();

	ProductoInternoDto crearProducto(ProductoInternoDto dto) throws Exception;

	ProductoInternoDto modificarProducto(ProductoInternoDto dto)
			throws Exception;

	List<ProductoInternoDto> eliminarProductos(
			List<Integer> productoInternoIds);

	List<ProductoInternoDto> getProductDtos(String search) throws Exception;

	List<ProductoInternoDto> updateManyProducto(List<ProductoInternoDto> dtos)
			throws Exception;

	List<CategoryDto> getCategoryDtoList();

	CategoryDto updateCategoryHasUnit(CategoryDto dto);

	List<DatosDistribuidoraDto> getDistribuidoraStatus();

	void eliminarIndices();

	boolean existsProducts(List<Integer> productIds);

	List<ProductoInterno> getProductByIds(List<Integer> productIds);

	void changeAvailable(Integer productId, Boolean isAvailable);

	List<ProductoInterno> getProducts(String search) throws Exception;

	List<ProductCustomerDto> getProductsForCustomer() throws Exception;
}
