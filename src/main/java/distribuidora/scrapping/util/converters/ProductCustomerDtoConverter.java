package distribuidora.scrapping.util.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import distribuidora.scrapping.dto.ProductCustomerDto;
import distribuidora.scrapping.entities.ProductoInterno;
import distribuidora.scrapping.entities.ProductoInternoStatus;
import distribuidora.scrapping.services.PresentationDtoConverter;
import distribuidora.scrapping.util.CalculatorUtil;

@Component
public class ProductCustomerDtoConverter extends Converter<ProductoInternoStatus, ProductCustomerDto>{
	
	@Autowired
	CalculatorUtil calculatorUtil;
	
	@Autowired
	LookupValueDtoConverter lookupValueDtoConverter;
	
	@Autowired
	CategoryDtoConverter categoryDtoConverter;
	
	@Autowired
	PresentationDtoConverter presentationDtoConverter;

	@Override
	public ProductCustomerDto toDto(ProductoInternoStatus entidad) {
		ProductCustomerDto dto = new ProductCustomerDto();
		ProductoInterno p = entidad.getProductoInterno();
		dto.setId(p.getId());
		dto.setName(p.getNombre());
		dto.setDescription(p.getDescripcion());
		dto.setStock(entidad.getHasStock());
		dto.setUnit(presentationDtoConverter.toDto(entidad.getProductoInterno().getPresentation()));
		dto.setPrice(calculatorUtil.calculateCustomerPrice(p));
		dto.setCategory(categoryDtoConverter.toDto(p.getCategory()));
		dto.setLastUpdate(entidad.getProductoInterno().getFechaActualizacion());
		return dto;
	}

	@Override
	public ProductoInternoStatus toEntidad(ProductCustomerDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
