package distribuidora.scrapping.util.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import distribuidora.scrapping.dto.ProductCustomerDto;
import distribuidora.scrapping.entities.ProductoInterno;
import distribuidora.scrapping.services.PresentationDtoConverter;
import distribuidora.scrapping.util.CalculatorUtil;

@Component
public class ProductCustomerDtoConverter extends Converter<ProductoInterno, ProductCustomerDto> {

	@Autowired
	CalculatorUtil calculatorUtil;

	@Autowired
	LookupValueDtoConverter lookupValueDtoConverter;

	@Autowired
	CategoryDtoConverter categoryDtoConverter;

	@Autowired
	PresentationDtoConverter presentationDtoConverter;

	@Override
	public ProductCustomerDto toDto(ProductoInterno p) {
		ProductCustomerDto dto = new ProductCustomerDto();
		dto.setId(p.getId());
		dto.setName(p.getNombre());
		dto.setDescription(p.getDescripcion());
		dto.setStock(p.getAvailable());
		dto.setUnit(presentationDtoConverter.toDto(p.getPresentation()));
		dto.setPrice(calculatorUtil.calculateCustomerPrice(p));
		dto.setCategory(categoryDtoConverter.toDto(p.getCategory()));
		dto.setLastUpdate(p.getFechaActualizacion());
		return dto;
	}

	@Override
	public ProductoInterno toEntidad(ProductCustomerDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
