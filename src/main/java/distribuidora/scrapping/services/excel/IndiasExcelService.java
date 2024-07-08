package distribuidora.scrapping.services.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
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

	private String validarCodigo(Cell cell) {
		String codigo = StringUtils.EMPTY;

		try {
			codigo = cell.getStringCellValue();
		} catch (Exception e) {
		}
		try {
			codigo = String.valueOf(cell.getNumericCellValue());
		} catch (Exception e) {
		}
		// Me fijo que el codigo al final no tenga .0
		codigo = codigo.replace(".0", "");
		return codigo;
	}

	private double validarPrecio(Cell cell) {
		try {
			return cell.getNumericCellValue();
		} catch (Exception e) {
			return 0.0;
		}
	}

	@Override
	public void setCodes() {
		setDistribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_INDIAS);
	}

	@Override
	protected ExternalProduct convertirRowEnProductoEspecifico(Row row,
			DatosDistribuidora data) {
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
		}

		double price = 0;
		String code = StringUtils.EMPTY;
		String rubro = StringUtils.EMPTY;
		String title = StringUtils.EMPTY;
		if (rowGeneral) {
			price = validarPrecio(row.getCell(6));
			code = validarCodigo(row.getCell(3));
			title = row.getCell(4).toString();
			rubro = row.getCell(1).toString();
		} else if (rowEspecifico) {
			price = validarPrecio(row.getCell(4));
			code = validarCodigo(row.getCell(2));
			title = row.getCell(3).toString();
			rubro = row.getCell(1).toString();
		}
		title = String.format("%s %s", rubro, title);
		return new ExternalProduct(null, title, price, null,
				getTipoDistribuidora(), code);
	}
}
