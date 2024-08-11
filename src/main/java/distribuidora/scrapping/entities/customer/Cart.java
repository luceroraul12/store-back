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

@Entity
@Table
@Data
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
	
	public Cart(Client client, Date dateCreated, String status) {
		super();
		this.client = client;
		this.dateCreated = dateCreated;
		this.status = status;
	}
	
}
