package distribuidora.scrapping.dto;

import lombok.Data;

@Data
public class DiscountDto {
	private Integer id;
	private String name;
	private String description;
	private Double percentageValue;
	private Double plainValue;
}
