package distribuidora.scrapping.services;

import java.util.List;

import distribuidora.scrapping.dto.SupplierBalanceDto;
import distribuidora.scrapping.dto.SupplierBalancePageDto;
import distribuidora.scrapping.dto.SupplierDto;

public interface SupplierService {
	List<SupplierDto> getSuppliers();
	SupplierDto createUpdateSupplier(SupplierDto dto) throws Exception;
	Integer deleteSupplier(Integer id) throws Exception;
	SupplierBalancePageDto getBalances(Integer supplierId, Integer page, Integer size) throws Exception;
	SupplierBalanceDto createBalance(Integer supplierId, SupplierBalanceDto dto) throws Exception;
	Integer deleteBalance(Integer supplierId, Integer balanceId) throws Exception;
}
