package distribuidora.scrapping.entities.customer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import distribuidora.scrapping.entities.LookupValor;
import distribuidora.scrapping.entities.ProductoInterno;
import distribuidora.scrapping.entities.Unit;
import distribuidora.scrapping.entities.Presentation;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
public class CartProduct {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "unit_id")
	private Unit unit;
	@ManyToOne
    @JoinColumn(name = "cart_id")
	private Cart cart;
	@ManyToOne
    @JoinColumn(name = "product_id")
	private ProductoInterno product;
	private Double price;
	private Double quantity;
	
	public CartProduct(Unit unit, Cart cart,
			ProductoInterno product, Double price, Double quantity) {
		super();
		this.unit = unit;
		this.cart = cart;
		this.product = product;
		this.price = price;
		this.quantity = quantity;
	}
	
	
}
