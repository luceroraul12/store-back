package distribuidora.scrapping.dto;

import lombok.Data;

@Data
public class CategoryDto {
	Integer id;
	String name;
	String description;
	UnitDto unit;
}
