package distribuidora.scrapping.dto;

import lombok.Data;

@Data
public class CartProductDto {
	private Integer cartProductId;
	private Integer backendCartProductId;
	private Integer productId;
	private String name;
	private String description;
	private Double quantity;
	private UnitDto unit;
	private Double price;
}
