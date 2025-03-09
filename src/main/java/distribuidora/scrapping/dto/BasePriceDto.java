package distribuidora.scrapping.dto;

import lombok.Data;

@Data
public class BasePriceDto {
	private String label;
	private Double labelPrice;
	private Double relation;
	private LookupValueDto lvUnit;
}
