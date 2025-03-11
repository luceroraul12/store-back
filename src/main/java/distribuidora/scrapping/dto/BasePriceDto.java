package distribuidora.scrapping.dto;

import lombok.Data;

@Data
public class BasePriceDto {
	private String label;
	private Double relation;
	private UnitDto unit;
}
