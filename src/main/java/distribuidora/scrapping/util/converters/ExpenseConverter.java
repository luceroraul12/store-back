package distribuidora.scrapping.util.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import distribuidora.scrapping.dto.ExpenseDto;
import distribuidora.scrapping.entities.Expense;

@Component
public class ExpenseConverter extends Converter<Expense, ExpenseDto> {

	@Autowired
	CategoryDtoConverter categoryDtoConverter;

	@Autowired
	LookupValueDtoConverter lookupValueDtoConverter;

	@Override
	public ExpenseDto toDto(Expense entidad) {
		ExpenseDto dto = new ExpenseDto();
		dto.setId(entidad.getId());
		dto.setCreationDate(entidad.getCreationDate());
		dto.setClientId(entidad.getClient().getId());
		dto.setAmount(entidad.getAmount());
		dto.setCategory(categoryDtoConverter.toDto(entidad.getCategory()));
		dto.setPaymentMethod(lookupValueDtoConverter.toDto(entidad.getPaymentMethod()));
		dto.setObservation(entidad.getObservation());
		return dto;
	}

	@Override
	public Expense toEntidad(ExpenseDto dto) {
		Expense entidad = new Expense();
		entidad.setId(dto.getId());
		entidad.setCreationDate(dto.getCreationDate());
		entidad.setAmount(dto.getAmount());
		entidad.setObservation(dto.getObservation());
		return entidad;
	}

}
