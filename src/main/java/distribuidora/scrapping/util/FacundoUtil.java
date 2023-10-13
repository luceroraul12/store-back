package distribuidora.scrapping.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.entities.productos.especificos.FacundoEntidad;

@Component
public class FacundoUtil extends ProductoExcelUtil<FacundoEntidad> {

	private String categoriaExcel;

	@Override
	public List<Producto> convertirProductoyDevolverlo(
			FacundoEntidad productoEntidad) {
		String distribuidoraCodigo = Constantes.LV_DISTRIBUIDORA_FACUNDO;
		List<Producto> productosGenerados = new ArrayList<>();
		String descripcionMayor = String.format("%s - %s",
				productoEntidad.getCategoriaRenglon(),
				productoEntidad.getCategoria());

		productosGenerados.add(Producto.builder().id(productoEntidad.getId())
				.descripcion(descripcionMayor)
				.precioPorCantidadEspecifica(
						productoEntidad.getPrecioMayor() != null
								? productoEntidad.getPrecioMayor()
								: 0.0)
				.distribuidoraCodigo(distribuidoraCodigo).build());

		return productosGenerados;
	}
	@Override
	public FacundoEntidad convertirRowEnProductoEspecifico(Row row,
			String distribuidoraCodigo) {

		Double precioMayor = validarPrecio(row.getCell(2));
		Double precioMenor = validarPrecio(row.getCell(3));
		return FacundoEntidad.builder().distribuidoraCodigo(distribuidoraCodigo)
				.categoria(row.getCell(0).getStringCellValue())
				.categoriaRenglon(this.categoriaExcel)
				.subcategoria(row.getCell(1).toString())
				.precioMayor(precioMayor).precioMenor(precioMenor).build();
	}

	private Double validarPrecio(Cell cell) {
		try {
			return cell.getNumericCellValue();
		} catch (Exception e) {
			return 0.0;
		}
	}

}
