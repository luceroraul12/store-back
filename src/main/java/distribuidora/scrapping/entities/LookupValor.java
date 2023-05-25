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
	private Long id;
	private String codigo;
	private String descripcion;
	@ManyToOne
	@JoinColumn(name = "lookup_tipo_id")
	private LookupTipo lookupTipo;

	public LookupValor(String codigo) {
		this.codigo = codigo;
	}
}
