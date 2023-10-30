package distribuidora.scrapping.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "productos_internos")
@NoArgsConstructor
public class ProductoInterno {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	@JoinColumn(name = "distribuidora_id")
	private LookupValor distribuidoraReferencia;
	@Column(name = "fecha_creacion")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCreacion;
	@Column(name = "fecha_modificacion")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaActualizacion;
	@ManyToOne
	@JoinColumn(name = "lv_categoria_id")
	private LookupValor lvCategoria;
	@Column(name = "precio_transporte")
	private Double precioTransporte;
	@Column(name = "precio_empaquetado")
	private Double precioEmpaquetado;
	@Column(name = "porcentaje_ganancia")
	private Double porcentajeGanancia;
	@ManyToOne
	@JoinColumn(name = "external_product_id")
	private ExternalProduct externalProduct;
	@Column(name="porcentaje_impuesto")
	private Double porcentajeImpuesto;
	
	@Builder
	public ProductoInterno(Integer id, String nombre, String descripcion,
			Double precio, String codigoReferencia,
			LookupValor distribuidoraReferencia, Date fechaCreacion,
			Date fechaActualizacion, Boolean isUnit,
			ExternalProduct externalProduct, LookupValor category, Double porcentajeImpuesto) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.codigoReferencia = codigoReferencia;
		this.distribuidoraReferencia = distribuidoraReferencia;
		this.fechaCreacion = fechaCreacion;
		this.fechaActualizacion = fechaActualizacion;
		this.externalProduct = externalProduct;
		this.lvCategoria = category;
		this.porcentajeImpuesto = porcentajeImpuesto;
	}
}
