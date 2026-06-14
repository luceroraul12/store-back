package distribuidora.scrapping.dto;

import lombok.Data;

@Data
public class CustomerDto {
	private Integer id;
	private String name;
	private String phone;
	private ClientDto client;
}
