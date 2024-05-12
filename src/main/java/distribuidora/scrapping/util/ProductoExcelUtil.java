package distribuidora.scrapping.util;

import org.apache.poi.ss.usermodel.Row;

import distribuidora.scrapping.entities.LookupValor;

public abstract class ProductoExcelUtil<Entidad> extends ProductoUtil<Entidad> {

	public abstract Entidad convertirRowEnProductoEspecifico(Row row,
			LookupValor distribuidora);
}
