package distribuidora.scrapping.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table
@Data
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	@Column
	String name;
	@Column
	String description;
	@ManyToOne
	@JoinColumn(name = "lv_unit_id")
	LookupValor unit;
	@ManyToOne
	@JoinColumn(name = "client_id")
	Client client;
}
