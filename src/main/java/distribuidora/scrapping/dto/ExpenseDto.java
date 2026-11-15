package distribuidora.scrapping.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ExpenseDto {
	private Integer id;
	private Date creationDate;
	private Integer clientId;
	private Double amount;
	private CategoryDto category;
	private LookupValueDto paymentMethod;
	private String observation;
}
