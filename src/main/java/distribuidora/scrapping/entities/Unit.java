package distribuidora.scrapping.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Unit {	
	@Id
	@Column(name = "id", nullable = false)
	private Integer id;
	private String name;
	private String description;
	private Double relation;
	private Boolean selectable;
	private Boolean pdfShowChild;
	@ManyToOne
	@JoinColumn(name = "unit_parent_id")
	private Unit unitParent;
	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;
}
