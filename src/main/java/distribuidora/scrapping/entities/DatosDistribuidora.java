package distribuidora.scrapping.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class DatosDistribuidora {
	@Id
	private Integer id;
	private boolean active;
	private LookupValor distribuidora;
	private LookupValor tipo;
	private Date fechaActualizacion;
	private Integer cantidadDeProductosAlmacenados;
	private boolean web;
	private boolean excel;
	private String webUrl;
}
