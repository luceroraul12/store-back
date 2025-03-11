package distribuidora.scrapping.dto;

import lombok.Data;

@Data
public class UnitDto {
	private Integer id;
	private String name;
	private String description;
	private Double relation;
	private Boolean selectable;
	private Boolean pdfShowChild;
	private UnitDto unitParent;
}
