package distribuidora.scrapping.entities.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class ProductoInternoDto {
	private Integer id;
	private String nombre;
	private String descripcion;
	private Double precio;
	private String codigoReferencia;
	private String distribuidoraReferenciaCodigo;
	private String distribuidoraReferenciaNombre;
	private Date fechaCreacion;
	private Date fechaActualizacion;
}
