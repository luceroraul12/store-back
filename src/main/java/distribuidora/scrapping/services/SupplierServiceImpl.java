package distribuidora.scrapping.services;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import distribuidora.scrapping.dto.SupplierBalanceDto;
import distribuidora.scrapping.dto.SupplierBalancePageDto;
import distribuidora.scrapping.dto.SupplierBalanceSummaryDto;
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
		if (balanceRepository.existsBySupplierIdAndClientId(id, userService.getCurrentClient().getId()))
			throw new Exception("La distribuidora cuenta con saldos asociados y no puede eliminarse");
		supplierRepository.delete(supplier);
		return id;
	}

	@Override
	public SupplierBalancePageDto getBalances(Integer supplierId, Integer page, Integer size) throws Exception {
		getSupplier(supplierId);
		Integer clientId = userService.getCurrentClient().getId();
		Page<SupplierBalance> balancePage = balanceRepository.findBySupplierIdAndClientId(
				supplierId, clientId, PageRequest.of(page, size));
		SupplierBalancePageDto result = new SupplierBalancePageDto();
		result.setContent(balanceDtoConverter.toDtoList(balancePage.getContent()));
		result.setNumber(balancePage.getNumber());
		result.setSize(balancePage.getSize());
		result.setTotalElements(balancePage.getTotalElements());
		result.setTotalPages(balancePage.getTotalPages());
		result.setSummary(buildSummary(supplierId, clientId));
		return result;
	}

	private SupplierBalanceSummaryDto buildSummary(Integer supplierId, Integer clientId) {
		Double totalToSupplier = balanceRepository.sumByType(supplierId, clientId, "SUPPLIER_CREDIT");
		Double totalToStore = balanceRepository.sumByType(supplierId, clientId, "STORE_CREDIT");
		SupplierBalanceSummaryDto summary = new SupplierBalanceSummaryDto();
		summary.setTotalToSupplier(totalToSupplier);
		summary.setTotalToStore(totalToStore);
		double difference = Math.abs(totalToSupplier - totalToStore);
		summary.setDifference(difference);
		summary.setDebtor(totalToSupplier.equals(totalToStore)
				? "NONE"
				: totalToSupplier > totalToStore ? "STORE" : "SUPPLIER");
		return summary;
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
