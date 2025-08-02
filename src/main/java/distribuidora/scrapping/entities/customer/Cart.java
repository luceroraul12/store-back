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
import distribuidora.scrapping.entities.Person;
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
	private Date dateCreated;
	private String status;
	private Double totalPrice;
	private Double discount;
	private Double totalPriceCustomer;

	public Cart(Client client, Person customer, Date dateCreated, String status, Double totalPrice,
			Double totalPriceCustomer, Double discount) {
		super();
		this.client = client;
		this.customer = customer;
		this.dateCreated = dateCreated;
		this.status = status;
		this.totalPrice = totalPrice;
		this.totalPriceCustomer = totalPriceCustomer;
		this.discount = discount;
	}

}
