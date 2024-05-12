package distribuidora.scrapping.services.excel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import distribuidora.scrapping.entities.ProductoEspecifico;
import distribuidora.scrapping.entities.UpdateRequest;
import distribuidora.scrapping.entities.UpdateRequestExcel;
import distribuidora.scrapping.services.BuscadorDeProductos;
import distribuidora.scrapping.util.ProductoExcelUtil;

/**
 * Encargada de configurar la busqueda por medio de documentos excel.
 * 
 * @param <Entidad>
 * @see UpdateRequestExcel
 */
public abstract class BusquedorPorExcel<Entidad extends ProductoEspecifico>
		extends
			BuscadorDeProductos<Entidad> {

	@Autowired
	ProductoExcelUtil<Entidad> util;

	@Override
	protected List<Entidad> adquirirProductosEntidad(UpdateRequest request) {
		List<Entidad> productosrecolectados;
		productosrecolectados = obtenerProductosApartirDeExcels(
				request.getMultipartFiles());
		return productosrecolectados;
	}

	/**
	 * Encargado de obtener la lista de productos en dicha Entidad. hace todos
	 * los pasos para convertir y analizar workbooks a partir de Multipart hasta
	 * obtener List Entidad
	 * 
	 * @param excels
	 *            Documentos a los cuales se los va aconvertir en WorkBook
	 * @return Lista de productos en entidad especifica
	 * @throws IOException
	 */
	public List<Entidad> obtenerProductosApartirDeExcels(
			MultipartFile[] excels) {
		return Arrays.stream(excels).map(this::obtenerSheets)
				.flatMap(Collection::stream).map(this::obtenerProductosPorSheet)
				.flatMap(Collection::stream).filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	/**
	 * Genera todos los sheets que contenga un documento Excel.
	 * 
	 * @param excel
	 *            un unico documento excel.
	 * @return una o varias sheets.
	 * @throws IOException
	 */
	private ArrayList<Sheet> obtenerSheets(MultipartFile excel) {
		Workbook workbook;
		try {
			workbook = new HSSFWorkbook(excel.getInputStream());
		} catch (Exception e) {
			try {
				workbook = new XSSFWorkbook(excel.getInputStream());
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}
		ArrayList<Sheet> sheets = new ArrayList<>();
		workbook.forEach(sheets::add);
		return sheets;
	}

	/**
	 * Extrae todos los productos del sheet
	 * 
	 * @param sheet
	 *            es uno solo
	 * @return lsita de productos
	 */
	private Collection<Entidad> obtenerProductosPorSheet(Sheet sheet) {
		Collection<Entidad> productosFinales = new ArrayList<>();
		sheet.rowIterator().forEachRemaining(row -> {
			row.cellIterator().forEachRemaining(cell -> {
				expandirValorDeCeldasFusionadas(sheet, cell);
				aplicarFormular(cell);
			});
			productosFinales.addAll(trabajarConRowyObtenerProducto(row));
		});
		return productosFinales;
	}

	/**
	 * Expande los valores de celdas fusionadas. Cuando un rango de celdas es
	 * fusionado, todas muestran el mismo valor. Pero el valor de todas las
	 * celdas solo se encuentra en la primera columna primer renglon de un rango
	 * de valores fusionados, el resto de celdas del rango tienen valor null.
	 * Este metodo se encarga de analizar una celda con respecto a los rangos de
	 * fusion para una sheet y en caso de encajar en uno (y no ser col 0 row 0)
	 * se le copia el valor correspondiente para que no tenga valor null.
	 * 
	 * @param sheet
	 * @param celda
	 */
	private void expandirValorDeCeldasFusionadas(Sheet sheet, Cell celda) {
		sheet.getMergedRegions().forEach(rango -> {
			Cell celdaUnica = sheet.getRow(rango.getFirstRow())
					.getCell(rango.getFirstColumn());
			if (rango.isInRange(celda)) {
				try {
					celda.setCellValue(celdaUnica.getStringCellValue());
				} catch (Exception e) {
					celda.setCellValue(celdaUnica.getNumericCellValue());
				}
			}
		});
	}

	/**
	 * En caso de que la celda contenga una formula, la aplica y deja el
	 * resultado en su lugar.
	 * 
	 * @param celda
	 */
	private void aplicarFormular(Cell celda) {
		if (celda.getCellType() == CellType.FORMULA) {
			celda.removeFormula();
		}
	}

	/**
	 * Trabaja con un renglon para obtener productos del mismo. Este renglon
	 * previamente es pasado por un condicional para indicar si valido realizar
	 * la extraccion.
	 * 
	 * @param row
	 *            del sheet
	 * @return lista de productos
	 * @see BusquedorPorExcel#esRowValido(Row row)
	 */
	private Collection<Entidad> trabajarConRowyObtenerProducto(Row row) {
		Collection<Entidad> productosPorRows = new ArrayList<>();
		if (esRowValido(row)) {
			Entidad producto = util.convertirRowEnProductoEspecifico(row,
					getDistribuidoraCodigo());
			productosPorRows.add(producto);
		}
		return productosPorRows;
	}

	/**
	 * Encargado de verificar si es un renglon valido para mapear. Es necesario
	 * debido a que algunos excels estan acompaniado de datos que no son
	 * necesarios para la generacion de productos o por que no cumplen con los
	 * requisitos minimos necesarios para que sea un producto.
	 * 
	 * @param row
	 *            renglon a verificar
	 * @return booleano
	 */
	abstract boolean esRowValido(Row row);

}
