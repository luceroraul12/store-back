package distribuidora.scrapping.services;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import distribuidora.scrapping.dto.SupplierBalanceDto;
import distribuidora.scrapping.dto.SupplierDto;
import distribuidora.scrapping.entities.Client;
import distribuidora.scrapping.entities.LookupValor;
import distribuidora.scrapping.entities.Supplier;
import distribuidora.scrapping.entities.SupplierBalance;
import distribuidora.scrapping.repositories.SupplierBalanceRepository;
import distribuidora.scrapping.repositories.SupplierRepository;
import distribuidora.scrapping.services.general.LookupService;
import distribuidora.scrapping.util.converters.SupplierBalanceDtoConverter;
import distribuidora.scrapping.util.converters.SupplierDtoConverter;

@Service
public class SupplierServiceImpl implements SupplierService {

	private static final String BALANCE_TYPE_LOOKUP = "SUPPLIER_BALANCE_TYPE";

	@Autowired
	private SupplierRepository supplierRepository;

	@Autowired
	private SupplierBalanceRepository balanceRepository;

	@Autowired
	private UsuarioService userService;

	@Autowired
	private LookupService lookupService;

	@Autowired
	private SupplierDtoConverter supplierDtoConverter;

	@Autowired
	private SupplierBalanceDtoConverter balanceDtoConverter;

	@Override
	public List<SupplierDto> getSuppliers() {
		return supplierDtoConverter.toDtoList(
				supplierRepository.findByClientId(userService.getCurrentClient().getId()));
	}

	@Override
	public SupplierDto createUpdateSupplier(SupplierDto dto) throws Exception {
		if (dto == null || StringUtils.isBlank(dto.getName()))
			throw new Exception("La distribuidora debe tener nombre");

		Client client = userService.getCurrentClient();
		Supplier supplier;
		if (dto.getId() == null) {
			supplier = supplierDtoConverter.toEntidad(dto);
		} else {
			supplier = supplierRepository.findByIdAndClientId(dto.getId(), client.getId());
			if (supplier == null)
				throw new Exception("La distribuidora no existe para la tienda actual");
			supplier.setName(dto.getName());
			supplier.setPhone(dto.getPhone());
			supplier.setEmail(dto.getEmail());
			supplier.setObservations(dto.getObservations());
		}
		supplier.setClient(client);
		return supplierDtoConverter.toDto(supplierRepository.save(supplier));
	}

	@Override
	@Transactional
	public Integer deleteSupplier(Integer id) throws Exception {
		Supplier supplier = supplierRepository.findByIdAndClientId(id, userService.getCurrentClient().getId());
		if (supplier == null)
			throw new Exception("La distribuidora no existe para la tienda actual");
		if (!balanceRepository.findBySupplierIdAndClientId(id, userService.getCurrentClient().getId()).isEmpty())
			throw new Exception("La distribuidora cuenta con saldos asociados y no puede eliminarse");
		supplierRepository.delete(supplier);
		return id;
	}

	@Override
	public List<SupplierBalanceDto> getBalances(Integer supplierId) throws Exception {
		getSupplier(supplierId);
		return balanceDtoConverter.toDtoList(balanceRepository.findBySupplierIdAndClientId(
				supplierId, userService.getCurrentClient().getId()));
	}

	@Override
	public SupplierBalanceDto createBalance(Integer supplierId, SupplierBalanceDto dto) throws Exception {
		Supplier supplier = getSupplier(supplierId);
		if (dto == null || dto.getAmount() == null || dto.getAmount() <= 0)
			throw new Exception("El saldo debe tener un monto mayor a cero");
		if (dto.getBalanceType() == null || StringUtils.isBlank(dto.getBalanceType().getCode()))
			throw new Exception("El saldo debe tener un tipo");

		LookupValor balanceType = lookupService.getLookupValueByCode(dto.getBalanceType().getCode());
		if (balanceType == null || balanceType.getLookupTipo() == null
				|| !BALANCE_TYPE_LOOKUP.equals(balanceType.getLookupTipo().getCodigo()))
			throw new Exception("El tipo de saldo no es válido");

		SupplierBalance balance = new SupplierBalance();
		balance.setSupplier(supplier);
		balance.setBalanceType(balanceType);
		balance.setAmount(dto.getAmount());
		return balanceDtoConverter.toDto(balanceRepository.save(balance));
	}

	@Override
	public Integer deleteBalance(Integer supplierId, Integer balanceId) throws Exception {
		getSupplier(supplierId);
		SupplierBalance balance = balanceRepository.findByIdAndSupplierIdAndClientId(
				balanceId, supplierId, userService.getCurrentClient().getId());
		if (balance == null)
			throw new Exception("El saldo no existe para la distribuidora actual");
		balanceRepository.delete(balance);
		return balanceId;
	}

	private Supplier getSupplier(Integer supplierId) throws Exception {
		Supplier supplier = supplierRepository.findByIdAndClientId(supplierId, userService.getCurrentClient().getId());
		if (supplier == null)
			throw new Exception("La distribuidora no existe para la tienda actual");
		return supplier;
	}
}
