package distribuidora.scrapping.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "lookup_tipo")
public class LookupTipo {

	@Id
	@Column(name = "id", nullable = false)
	private Integer id;
	private String codigo;
	private String descripcion;

}
