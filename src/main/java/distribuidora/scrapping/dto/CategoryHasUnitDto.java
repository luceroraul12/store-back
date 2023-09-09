package distribuidora.scrapping.dto;

import lombok.Data;

@Data
public class CategoryHasUnitDto {
	private Integer id;
	private LookupValueDto category;
	private LookupValueDto unit;
}
