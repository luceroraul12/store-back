package distribuidora.scrapping.entities.customer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import distribuidora.scrapping.entities.ProductoInterno;
import lombok.Data;

@Entity
@Table
@Data
public class OrderHasProduct {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
	private Integer id;
	private String unitName;
	private Double unitValue;
	private Integer unitPrice;
	private Double amount;
	@ManyToOne
    @JoinColumn(name = "order_id")
	private Order order;
	@ManyToOne
    @JoinColumn(name = "product_id")
	private ProductoInterno product;
	private Integer percentDiscount;
}
