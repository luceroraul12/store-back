package distribuidora.scrapping.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class LookupValueDto {
	private Integer id;
	private String code;
	private String description;
}
