package distribuidora.scrapping.dto;

import lombok.Data;

@Data
public class ProductOrderDto {
	private Integer id;
	private String unitName;
	private String unitValue;
	private Integer unitPrice;
	private Double amount;
}
