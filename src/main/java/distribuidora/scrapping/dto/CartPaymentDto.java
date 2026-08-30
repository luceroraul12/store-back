package distribuidora.scrapping.dto;

import lombok.Data;

@Data
public class CartPaymentDto {
	private Integer id;
	private LookupValueDto paymentMethod;
	private Double amount;
}
