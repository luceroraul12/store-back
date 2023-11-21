package distribuidora.scrapping.entities.customer;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table
@Data
public class Order {
	private Integer id;
	private Customer customer;
	private Date date;
}
