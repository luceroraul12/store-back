package distribuidora.scrapping.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class LookupValor {

	@Id
	@Column(name = "id", nullable = false)
	private Integer id;
	@Column
	private String codigo;
	@Column
	private String descripcion;
	@Column
	private String valor;
	@ManyToOne
	@JoinColumn(name = "lookup_tipo_id")
	private LookupTipo lookupTipo;

	public LookupValor(String codigo) {
		this.codigo = codigo;
	}
}
