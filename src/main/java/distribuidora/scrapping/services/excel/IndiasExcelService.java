package distribuidora.scrapping.services.excel;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.DatosDistribuidora;
import distribuidora.scrapping.entities.ExternalProduct;

@Service
public class IndiasExcelService extends ProductSearcherExcel {

	@Override
	boolean esRowValido(Row row) {
		boolean resultado = false;
		boolean rowGeneral = false;
		boolean rowEspecifico = false;

		try {
			rowGeneral = row.getCell(1).getCellType().equals(CellType.STRING)
					&& (row.getCell(3).getCellType().equals(CellType.STRING)
							|| row.getCell(3).getCellType()
									.equals(CellType.NUMERIC))
					&& row.getCell(4).getCellType().equals(CellType.STRING)
					&& row.getCell(6).getCellType().equals(CellType.NUMERIC);
		} catch (Exception exception) {
		}

		try {
			rowEspecifico = row.getCell(1).getCellType().equals(CellType.STRING)
					&& (row.getCell(2).getCellType().equals(CellType.STRING)
							|| row.getCell(2).getCellType()
									.equals(CellType.NUMERIC))
					&& row.getCell(3).getCellType().equals(CellType.STRING)
					&& row.getCell(4).getCellType().equals(CellType.NUMERIC);
		} catch (Exception exception) {
		} ;
		resultado = rowGeneral || rowEspecifico;
		return resultado;
	}

	@Override
	public void setCodes() {
		setDistribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_INDIAS);
	}

	@Override
	protected ExternalProduct convertirRowEnProductoEspecifico(Row row,
			DatosDistribuidora data) {
		// TODO Auto-generated method stub
		return null;
	}
}
