package distribuidora.scrapping.entities.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoInternoDto {
	private Integer id;
	private String nombre;
	private String descripcion;
	private Double precio;
	private String codigoReferencia;
	private String distribuidoraReferenciaCodigo;
	private String distribuidoraReferenciaNombre;
	private String categoriaCodigo;
	private String categoriaNombre;
	private Date fechaCreacion;
	private Date fechaActualizacion;
}
