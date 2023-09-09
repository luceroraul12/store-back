package distribuidora.scrapping.util.converters;

import org.springframework.stereotype.Component;

import distribuidora.scrapping.dto.LookupValueDto;
import distribuidora.scrapping.entities.LookupValor;

@Component
public class LookupValueDtoConverter extends Converter<LookupValor, LookupValueDto>{

	@Override
	public LookupValueDto toDto(LookupValor entidad) {
		LookupValueDto dto = new LookupValueDto();
		dto.setId(entidad.getId());
		dto.setDescription(entidad.getDescripcion());
		dto.setCode(entidad.getCodigo());
		return dto;
	}

	@Override
	public LookupValor toEntidad(LookupValueDto dto) {
		LookupValor entidad = new LookupValor();
		entidad.setId(dto.getId());
		return entidad;
	}

}
