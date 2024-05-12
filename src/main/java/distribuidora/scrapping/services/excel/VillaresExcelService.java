package distribuidora.scrapping.services.excel;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.entities.productos.especificos.VillaresEntidad;

@Service
public class VillaresExcelService extends BusquedorPorExcel<VillaresEntidad> {
	@Override
	boolean esRowValido(Row row) {
		return row != null && row.getCell(1) != null
				&& row.getCell(1).getCellType().equals(CellType.NUMERIC);
	}
}
