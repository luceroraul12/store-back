package distribuidora.scrapping.dto;

import lombok.Data;

@Data
public class ProductOrderDto {
	private Integer id;
	private Integer productId;
	private String productName;
	private String productDescription;
	private String unitName;
	private Double unitValue;
	private Integer unitPrice;
	private Double amount;
}
