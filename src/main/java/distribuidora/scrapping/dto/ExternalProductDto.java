package distribuidora.scrapping.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
public class ExternalProductDto {
    private Integer id;
    private String title;
    private Double price;
    private Date date;
    private LookupValueDto distribuidora;
    private String code;
}
