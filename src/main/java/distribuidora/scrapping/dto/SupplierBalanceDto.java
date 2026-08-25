package distribuidora.scrapping.dto;

import java.util.Date;

import lombok.Data;

@Data
public class SupplierBalanceDto {
	private Integer id;
	private Integer supplierId;
	private LookupValueDto balanceType;
	private Double amount;
	private Date createdAt;
	private Date updatedAt;
}
