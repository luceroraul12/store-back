package distribuidora.scrapping.util.converters;

import org.springframework.stereotype.Component;

import distribuidora.scrapping.dto.SupplierDto;
import distribuidora.scrapping.entities.Supplier;

@Component
public class SupplierDtoConverter extends Converter<Supplier, SupplierDto> {

	@Override
	public SupplierDto toDto(Supplier entity) {
		SupplierDto dto = new SupplierDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setPhone(entity.getPhone());
		dto.setEmail(entity.getEmail());
		dto.setObservations(entity.getObservations());
		return dto;
	}

	@Override
	public Supplier toEntidad(SupplierDto dto) {
		Supplier entity = new Supplier();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setPhone(dto.getPhone());
		entity.setEmail(dto.getEmail());
		entity.setObservations(dto.getObservations());
		return entity;
	}
}
