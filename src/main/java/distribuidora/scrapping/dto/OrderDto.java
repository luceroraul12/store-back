package distribuidora.scrapping.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderDto {
	private Integer id;
	private String username;
	private String storeCode;
	private List<ProductOrderDto> products;
}
