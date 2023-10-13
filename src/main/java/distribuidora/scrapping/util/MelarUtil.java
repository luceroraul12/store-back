package distribuidora.scrapping.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.entities.productos.especificos.MelarEntidad;

@Component
public class MelarUtil extends ProductoUtil<MelarEntidad> {
	@Override
	public List<Producto> convertirProductoyDevolverlo(
			MelarEntidad productoSinConvertir) {
		List<Producto> productosCreados = new ArrayList<>();

		/*
		 * Hay que tener cuidado cons los precios, debido a que el monto siempre
		 * lo muestran por 1 kilo, Se debe multiplicar por la medida
		 * correspondiente para obtener la el precio que se paga por la minima
		 * cantidad adquirible
		 */
		HashMap<String, Double> preciosCalculados = new HashMap<>();
		preciosCalculados.put("fraccion", 0.0);
		preciosCalculados.put("granel", 0.0);

		verificaryCalcularPrecio(productoSinConvertir, preciosCalculados);

		/*
		 * Quiero tener un descripcion en el siguiente orden 1.producto 2.origen
		 * 3.cantidad 4.precio
		 */

		String descripcionFraccion = String.format("%s - %s X %s%s",
				productoSinConvertir.getProducto(),
				productoSinConvertir.getOrigen(),
				productoSinConvertir.getFraccion(),
				productoSinConvertir.getMedida());

		String descripcionGranel = String.format("%s - %s X %s%s",
				productoSinConvertir.getProducto(),
				productoSinConvertir.getOrigen(),
				productoSinConvertir.getGranel(),
				productoSinConvertir.getMedida());

		productosCreados.add(Producto.builder()
				.id(productoSinConvertir.getCodigo() + "F")
				.descripcion(descripcionFraccion)
				.precioPorCantidadEspecifica(preciosCalculados.get("fraccion"))
				.distribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_MELAR)
				.build());
		productosCreados.add(Producto.builder()
				.id(productoSinConvertir.getCodigo() + "G")
				.descripcion(descripcionGranel)
				.precioPorCantidadEspecifica(preciosCalculados.get("granel"))
				.distribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_MELAR)
				.build());

		return productosCreados;
	}

	private void verificaryCalcularPrecio(MelarEntidad productoSinConvertir,
			HashMap<String, Double> preciosCalculados) {
		try {
			preciosCalculados.replace("granel",
					productoSinConvertir.getPrecioGranel() * Double
							.parseDouble(productoSinConvertir.getGranel()));
		} catch (Exception e) {
			preciosCalculados.replace("granel", 0.0);
		}

		try {
			preciosCalculados.replace("fraccion",
					productoSinConvertir.getPrecioFraccionado() * Double
							.parseDouble(productoSinConvertir.getFraccion()));
		} catch (Exception e) {
			preciosCalculados.replace("fraccion", 0.0);
		}
	}
}
