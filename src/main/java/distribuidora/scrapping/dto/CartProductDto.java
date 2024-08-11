package distribuidora.scrapping.dto;

import lombok.Data;

@Data
public class CartProductDto {
	private Integer cartProductId;
	private Integer backendCartProductId;
	private Integer productId;
	private Double quantity;
	private Integer price;
}
