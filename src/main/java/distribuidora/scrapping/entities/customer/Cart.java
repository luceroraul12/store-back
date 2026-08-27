package distribuidora.scrapping.entities.customer;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import distribuidora.scrapping.entities.Client;
import distribuidora.scrapping.entities.Discount;
import distribuidora.scrapping.entities.Person;
import distribuidora.scrapping.entities.Supplier;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Person customer;
	@ManyToOne
	@JoinColumn(name = "supplier_id")
	private Supplier supplier;
	@ManyToOne
	private Discount discount;
	private Date dateCreated;
	private String status;
	private Double totalPrice;
	private Double customerTotalPrice;

	public Cart(Client client, Person customer, Supplier supplier, Date dateCreated, String status, Double totalPrice,
			Double customerTotalPrice, Discount discount) {
		this.client = client;
		this.customer = customer;
		this.supplier = supplier;
		this.dateCreated = dateCreated;
		this.status = status;
		this.totalPrice = totalPrice;
		this.discount = discount;
		this.customerTotalPrice = customerTotalPrice;
	}

}
