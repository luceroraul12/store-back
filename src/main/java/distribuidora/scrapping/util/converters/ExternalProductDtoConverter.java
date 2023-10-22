package distribuidora.scrapping.util.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import distribuidora.scrapping.dto.ExternalProductDto;
import distribuidora.scrapping.entities.ExternalProduct;

@Component
public class ExternalProductDtoConverter extends Converter<ExternalProduct, ExternalProductDto>{

	@Autowired
	private LookupValueDtoConverter lookupValueDtoConverter;
	
	@Override
	public ExternalProductDto toDto(ExternalProduct entidad) {
		ExternalProductDto dto = null;
		if(entidad != null) {
			dto = new ExternalProductDto();
			dto.setId(entidad.getId());
			dto.setCode(entidad.getCode());
			dto.setDate(entidad.getDate());
			dto.setDistribuidora(lookupValueDtoConverter.toDto(entidad.getDistribuidora()));
			dto.setPrice(entidad.getPrice());
			dto.setTitle(entidad.getTitle());
		}
		return dto;
	}

	@Override
	public ExternalProduct toEntidad(ExternalProductDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
