package distribuidora.scrapping.dto;

import lombok.Data;

@Data
public class SupplierDto {
	private Integer id;
	private String name;
	private String phone;
	private String email;
	private String observations;
}
