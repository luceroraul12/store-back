package distribuidora.scrapping.dto;

import lombok.Data;

@Data
public class PresentationDto {
	private Integer id;
	private String name;
	private String description;
	private Double relation;
	private UnitDto unit;
}
