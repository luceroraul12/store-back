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
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cash_register_session")
@Getter
@Setter
public class CashRegisterSession {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;
	@Column(name = "opening_date", nullable = false)
	private Date openingDate;
	@Column(name = "closing_date")
	private Date closingDate;
	@Column(name = "initial_amount", nullable = false)
	private Double initialAmount;
	@Column(name = "counted_amount")
	private Double countedAmount;
	@Column(name = "status", nullable = false)
	private String status;
	@Column(name = "opened_by", nullable = false)
	private String openedBy;
	@Column(name = "closed_by")
	private String closedBy;
}
