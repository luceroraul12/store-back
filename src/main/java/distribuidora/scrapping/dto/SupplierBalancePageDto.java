package distribuidora.scrapping.dto;

import java.util.List;

import lombok.Data;

@Data
public class SupplierBalancePageDto {
	private List<SupplierBalanceDto> content;
	private Integer number;
	private Integer size;
	private Long totalElements;
	private Integer totalPages;
	private SupplierBalanceSummaryDto summary;
}
