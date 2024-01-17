package distribuidora.scrapping.util.converters;

import org.springframework.stereotype.Component;

import distribuidora.scrapping.dto.ProductOrderDto;
import distribuidora.scrapping.entities.ProductoInternoStatus;

@Component
public class ProductHasStatusToProductOrderConverter
		extends
			Converter<ProductoInternoStatus, ProductOrderDto> {

	@Override
	public ProductOrderDto toDto(ProductoInternoStatus entidad) {
		ProductOrderDto dto = new ProductOrderDto();
		dto.setProductId(entidad.getProductoInterno().getId());
		dto.setProductName(entidad.getProductoInterno().getNombre());
		dto.setProductDescription(
				entidad.getProductoInterno().getDescripcion());
		return dto;
	}

	@Override
	public ProductoInternoStatus toEntidad(ProductOrderDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
