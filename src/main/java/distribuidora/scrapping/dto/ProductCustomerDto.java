package distribuidora.scrapping.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ProductCustomerDto {
	private Integer id;
	private Boolean stock;
	private String name;
	private String description;
	private Integer price;
	private CategoryDto category;
	private Date lastUpdate;
	private List<BasePriceDto> basePrices;
	private PresentationDto unit;
}
