package distribuidora.scrapping.entities.dto;

import distribuidora.scrapping.entities.LookupValor;
import lombok.*;

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
	private LookupValor lvCategoria;
	private Date fechaCreacion;
	private Date fechaActualizacion;
}
