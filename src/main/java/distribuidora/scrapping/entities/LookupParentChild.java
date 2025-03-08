package distribuidora.scrapping.entities;

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
public class LookupParentChild {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private LookupValor parent;
	@ManyToOne
	@JoinColumn(name = "child_id")
	private LookupValor child;
}
