package distribuidora.scrapping.dto;

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
	private String referenciaNombre;
	private LookupValueDto category;
	private Date fechaCreacion;
	private Date fechaActualizacion;
	private Double precioTransporte;
	private Double precioEmpaquetado;
	private Double porcentajeGanancia;
}
