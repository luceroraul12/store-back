package distribuidora.scrapping.util.converters;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import distribuidora.scrapping.dto.SupplierBalanceDto;
import distribuidora.scrapping.entities.SupplierBalance;

@Component
public class SupplierBalanceDtoConverter extends Converter<SupplierBalance, SupplierBalanceDto> {

	@Autowired
	private LookupValueDtoConverter lookupValueDtoConverter;

	@Override
	public SupplierBalanceDto toDto(SupplierBalance entity) {
		SupplierBalanceDto dto = new SupplierBalanceDto();
		dto.setId(entity.getId());
		dto.setSupplierId(entity.getSupplier().getId());
		dto.setBalanceType(lookupValueDtoConverter.toDto(entity.getBalanceType()));
		dto.setAmount(entity.getAmount());
		if (entity.getCart() != null)
			dto.setCartId(entity.getCart().getId());
		dto.setPrice(entity.getAmount());
		dto.setCreatedAt(entity.getCreatedAt());
		dto.setUpdatedAt(entity.getUpdatedAt());
		return dto;
	}

	@Override
	public SupplierBalance toEntidad(SupplierBalanceDto dto) {
		SupplierBalance entity = new SupplierBalance();
		entity.setId(dto.getId());
		entity.setAmount(dto.getAmount());
		return entity;
	}
}
