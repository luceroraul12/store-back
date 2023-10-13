package distribuidora.scrapping.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	private String referenciaNombre;
	private LookupValueDto category;
	private Date fechaCreacion;
	private Date fechaActualizacion;
	private Double precioTransporte;
	private Double precioEmpaquetado;
	private Double porcentajeGanancia;
}
