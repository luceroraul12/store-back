package distribuidora.scrapping.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class CartDto {
	private Integer cartId;
	private Integer backendCartId;
	private List<CartProductDto> products;
	private Date dateCreated;
	private String status;
	private Double totalPrice;
	private PersonDto customer;
}
