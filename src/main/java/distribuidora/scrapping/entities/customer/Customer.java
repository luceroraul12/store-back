package distribuidora.scrapping.entities.customer;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table
@Data
public class Customer {
	private Integer id;
	private String username;
}
