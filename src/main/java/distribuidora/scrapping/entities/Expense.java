package distribuidora.scrapping.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "expense")
public class Expense {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	@Column(name = "creation_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	@ManyToOne
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;
	@Column(name = "amount", nullable = false)
	private Double amount;
	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;
	@ManyToOne
	@JoinColumn(name = "payment_method_id", nullable = false)
	private LookupValor paymentMethod;
	@Column(name = "observation", length = 1000)
	private String observation;
}
