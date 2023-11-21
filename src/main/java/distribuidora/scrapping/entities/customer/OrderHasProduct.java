package distribuidora.scrapping.entities.customer;

import javax.persistence.Entity;
import javax.persistence.Table;

import distribuidora.scrapping.entities.ProductoInterno;
import lombok.Data;

@Entity
@Table
@Data
public class OrderHasProduct {
	private Integer id;
	private String unitName;
	private Double unitValue;
	private Integer unitPrice;
	private Double amount;
	private Order order;
	private ProductoInterno product;
}
