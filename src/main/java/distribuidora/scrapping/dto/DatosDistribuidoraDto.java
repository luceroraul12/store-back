package distribuidora.scrapping.dto;

import java.util.Date;

import lombok.Data;

@Data
public class DatosDistribuidoraDto {
	private Integer id;
	private LookupValueDto distribuidora;
	private Date dateLastUpdate;
	private Integer size;
	private boolean web;
	private boolean excel;
}
