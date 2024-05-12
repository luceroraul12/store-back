package distribuidora.scrapping.util;

import java.util.Collections;
import java.util.List;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.DatosDistribuidora;
import distribuidora.scrapping.entities.ExternalProduct;
import distribuidora.scrapping.entities.LookupValor;
import distribuidora.scrapping.entities.productos.especificos.VillaresEntidad;

@Component
public class VillaresUtil extends ProductoExcelUtil<VillaresEntidad> {

	@Override
	public VillaresEntidad convertirRowEnProductoEspecifico(Row row,
			DatosDistribuidora data) {
		Integer cantidadCeldas = row.getPhysicalNumberOfCells();
		Integer celdasGranel = 12;
		Integer celdasGourmetFraccionado = 11;
		Integer celdasDistribuido = 10;
		Integer indiceInicioCeldaPrecio = 0;
		// inicializo
		String id = String
				.valueOf(Math.round(row.getCell(1).getNumericCellValue()) + "R-"
						+ row.getRowNum());
		String descripcion = "";
		String cantidad = "";
		String cantidadMinima = "";
		String detalle = "";
		String marca = "";
		String unidad = "";
		Double precioLista = 0.0;
		Double precioPrimero = 0.0;
		Double precioSegundo = 0.0;
		Double precioTercero = 0.0;

		// producto granel
		if (cantidadCeldas.equals(celdasGranel)) {
			indiceInicioCeldaPrecio = celdasGranel - 4;
			descripcion = row.getCell(2).toString();
			cantidad = row.getCell(3).toString();
			cantidadMinima = row.getCell(4).toString();
			detalle = row.getCell(5).toString();
			marca = row.getCell(6).toString();
			unidad = row.getCell(7).toString();
		}
		// producto gourmet o fraccionado
		if (cantidadCeldas.equals(celdasGourmetFraccionado)) {
			indiceInicioCeldaPrecio = celdasGourmetFraccionado - 4;
			descripcion = row.getCell(2).toString();
			cantidad = row.getCell(3).toString();
			detalle = row.getCell(4).toString();
			marca = row.getCell(5).toString();
			unidad = row.getCell(6).toString();
		}
		// producto distribuidos
		if (cantidadCeldas.equals(celdasDistribuido)) {
			indiceInicioCeldaPrecio = celdasDistribuido - 4;
			descripcion = row.getCell(2).toString();
			cantidad = row.getCell(3).toString();
			unidad = row.getCell(4).toString();
			marca = row.getCell(5).toString();
		}

		Boolean estaDisponible = row.getCell(indiceInicioCeldaPrecio)
				.getCellType().equals(CellType.NUMERIC);
		if (estaDisponible) {
			precioLista = row.getCell(indiceInicioCeldaPrecio)
					.getNumericCellValue();
			try {
				precioPrimero = row.getCell(indiceInicioCeldaPrecio + 1)
						.getNumericCellValue();
				precioSegundo = row.getCell(indiceInicioCeldaPrecio + 2)
						.getNumericCellValue();
				precioTercero = row.getCell(indiceInicioCeldaPrecio + 3)
						.getNumericCellValue();
			} catch (Exception ignored) {
			}
		}

		return new VillaresEntidad(id, Constantes.LV_DISTRIBUIDORA_VILLARES,
				descripcion, cantidad, cantidadMinima, detalle, marca, unidad,
				precioLista, precioPrimero, precioSegundo, precioTercero);
	}

	@Override
	public List<ExternalProduct> convertirProductoyDevolverlo(
			VillaresEntidad productoSinConvertir) {
		String marca = !productoSinConvertir.getMarca().isEmpty()
				? productoSinConvertir.getMarca() + ":"
				: "";
		String cantidades = !productoSinConvertir.getCantidadMinima().isEmpty()
				? productoSinConvertir.getCantidad() + "-"
						+ productoSinConvertir.getCantidadMinima()
				: productoSinConvertir.getCantidad();
		String descripcion = String.format("%s %s - %s [%s] EN: %s", marca,
				productoSinConvertir.getDescripcion(),
				productoSinConvertir.getDetalle(), cantidades,
				productoSinConvertir.getUnidad());
		return Collections.singletonList(ExternalProduct.builder()
				.code(productoSinConvertir.getId()).title(descripcion)
				.price(productoSinConvertir.getPrecioLista())
				.distribuidora(
						new LookupValor(Constantes.LV_DISTRIBUIDORA_VILLARES))
				.build());
	}
}
