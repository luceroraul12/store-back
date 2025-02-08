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
import lombok.AllArgsConstructor;
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
	private Date dateCreated;
	private String status;
	private Double totalPrice;
	
	public Cart(Client client, Date dateCreated, String status, Double totalPrice) {
		super();
		this.client = client;
		this.dateCreated = dateCreated;
		this.status = status;
		this.totalPrice = totalPrice;
	}
	
}
