package distribuidora.scrapping.entities.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ProductoInternoDto {
	private Integer id;
	private String nombre;
	private String descripcion;
	private Double precio;
	private String codigoReferencia;
	private Integer distribuidoraReferenciaId;
	private String distribuidoraReferenciaNombre;
	private LocalDateTime fechaCreacion;
	private LocalDateTime fechaActualizacion;
}
