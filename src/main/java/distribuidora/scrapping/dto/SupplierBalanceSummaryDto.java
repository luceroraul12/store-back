package distribuidora.scrapping.dto;

import lombok.Data;

@Data
public class SupplierBalanceSummaryDto {
	private Double totalToSupplier;
	private Double totalToStore;
	private Double difference;
	private String debtor;
}
