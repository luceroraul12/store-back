package distribuidora.scrapping.dto;

import lombok.Data;

@Data
public class ProductoInternoStatusDto {
    private Integer id;
    private ProductoInternoDto productoInterno;
    private Boolean isUnit;
    private Boolean hasStock;
    private Double amount;
}
