package distribuidora.scrapping.util;

import java.util.Collections;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.ExternalProduct;
import distribuidora.scrapping.entities.LookupValor;
import distribuidora.scrapping.entities.productos.especificos.IndiasEntidad;

@Component
public class IndiasUtil extends ProductoExcelUtil<IndiasEntidad> {
	@Override
	public List<ExternalProduct> convertirProductoyDevolverlo(
			IndiasEntidad productoEntidad) {
		String descripcion = String.format("%s",
				productoEntidad.getDescripcion());
		return Collections.singletonList(ExternalProduct.builder()
				.code(productoEntidad.getCodigo().toString())
				.title(descripcion)
				.price(productoEntidad.getPrecio())
				.distribuidora(new LookupValor(Constantes.LV_DISTRIBUIDORA_INDIAS))
				.build());
	}

	@Override
	public IndiasEntidad convertirRowEnProductoEspecifico(Row row,
			LookupValor distribuidoraCodigo) {
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

		double precio;
		Integer codigo;
		if (rowGeneral) {
			precio = validarPrecio(row.getCell(6));
			codigo = validarCodigo(row.getCell(3));
			return IndiasEntidad.builder()
					.distribuidoraCodigo(distribuidoraCodigo)
					.rubro(row.getCell(1).toString()).codigo(codigo)
					.descripcion(row.getCell(4).toString()).precio(precio)
					.build();
		} else if (rowEspecifico) {
			precio = validarPrecio(row.getCell(4));
			codigo = validarCodigo(row.getCell(2));
			return IndiasEntidad.builder()
					.distribuidoraCodigo(distribuidoraCodigo)
					.rubro(row.getCell(1).toString()).codigo(codigo)
					.descripcion(row.getCell(3).toString()).precio(precio)
					.build();
		}
		return null;
	}

	private Integer validarCodigo(Cell cell) {
		Integer codigo = 0;

		try {
			codigo = Integer.valueOf(cell.getStringCellValue());
		} catch (Exception e) {
		}
		try {
			codigo = (int) cell.getNumericCellValue();
		} catch (Exception e) {
		}
		return codigo;
	}

	private double validarPrecio(Cell cell) {
		try {
			return cell.getNumericCellValue();
		} catch (Exception e) {
			return 0.0;
		}
	}
}
