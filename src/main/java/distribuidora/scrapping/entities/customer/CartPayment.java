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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_payment")
@Data
@NoArgsConstructor
public class CartPayment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "cart_id", nullable = false)
	private Cart cart;
	@ManyToOne
	@JoinColumn(name = "lv_payment_method_id", nullable = false)
	private LookupValor paymentMethod;
	@Column(name = "amount", nullable = false)
	private Double amount;

	public CartPayment(Cart cart, LookupValor paymentMethod, Double amount) {
		this.cart = cart;
		this.paymentMethod = paymentMethod;
		this.amount = amount;
	}
}
