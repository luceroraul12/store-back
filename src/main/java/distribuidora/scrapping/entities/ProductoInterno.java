package distribuidora.scrapping.entities;

import distribuidora.scrapping.enums.Distribuidora;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProductoInterno {

	private Integer id;
	private String nombre;
	private String descripcion;
	private Double precio;
	private String codigoReferencia;
	private Distribuidora distribuidoraReferencia;

	private LocalDate fechaCreacion;
	private LocalDate fechaActualizacion;
	
	@Builder
	public ProductoInterno(Integer id, String nombre, String descripcion, Double precio, String codigoReferencia,
			Distribuidora distribuidoraReferencia) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.codigoReferencia = codigoReferencia;
		this.distribuidoraReferencia = distribuidoraReferencia;
	}
	
	
	
}
