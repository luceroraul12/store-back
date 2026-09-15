package distribuidora.scrapping.util.converters;

import org.springframework.stereotype.Component;

import distribuidora.scrapping.dto.CashRegisterSessionDto;
import distribuidora.scrapping.entities.customer.CashRegisterSession;

@Component
public class CashRegisterSessionConverter extends Converter<CashRegisterSession, CashRegisterSessionDto> {

	@Override
	public CashRegisterSessionDto toDto(CashRegisterSession entidad) {
		CashRegisterSessionDto dto = new CashRegisterSessionDto();
		dto.setId(entidad.getId());
		dto.setClientId(entidad.getClient().getId());
		dto.setOpeningDate(entidad.getOpeningDate());
		dto.setClosingDate(entidad.getClosingDate());
		dto.setInitialAmount(entidad.getInitialAmount());
		dto.setCountedAmount(entidad.getCountedAmount());
		dto.setStatus(entidad.getStatus());
		dto.setOpenedBy(entidad.getOpenedBy());
		dto.setClosedBy(entidad.getClosedBy());
		return dto;
	}

	@Override
	public CashRegisterSession toEntidad(CashRegisterSessionDto dto) {
		return null;
	}
}
