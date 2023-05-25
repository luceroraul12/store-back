package distribuidora.scrapping.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity(name = "productos_internos")
@NoArgsConstructor
public class ProductoInterno {

	@Id
	private Integer id;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "descripcion")
	private String descripcion;
	@Column(name = "precio")
	private Double precio;
	@Column(name = "id_externo")
	private String codigoReferencia;
	@ManyToOne
	@JoinColumn(name = "distribuidora_referencia_id")
	private LookupValor distribuidoraReferencia;
	@Column(name = "fecha_creacion")
	private Date fechaCreacion;
	@Column(name = "fecha_modificacion")
	private Date fechaActualizacion;
	
	@Builder
	public ProductoInterno(Integer id, String nombre, String descripcion, Double precio, String codigoReferencia,
			LookupValor distribuidoraReferencia, Date fechaCreacion, Date fechaActualizacion) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.codigoReferencia = codigoReferencia;
		this.distribuidoraReferencia = distribuidoraReferencia;
		this.fechaCreacion = fechaCreacion;
		this.fechaActualizacion = fechaActualizacion;
	}
}
