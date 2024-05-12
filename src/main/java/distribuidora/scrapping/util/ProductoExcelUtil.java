package distribuidora.scrapping.util;

import org.apache.poi.ss.usermodel.Row;

import distribuidora.scrapping.entities.DatosDistribuidora;

public abstract class ProductoExcelUtil<Entidad> extends ProductoUtil<Entidad> {

	public abstract Entidad convertirRowEnProductoEspecifico(Row row,
			DatosDistribuidora data);
}
