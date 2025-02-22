package distribuidora.scrapping.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ProductCustomerDto {
	private Integer id;
	private Boolean stock;
	private Boolean onlyUnit;
	private String name;
	private String description;
	private Integer price;
	private CategoryDto category;
	private LookupValueDto unitType;
	private Date lastUpdate;
}
