package distribuidora.scrapping.services.webscrapping;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.ExternalProduct;
import distribuidora.scrapping.entities.productos.especificos.DonGasparEntidad;

@Service
public class DonGasparWebScrappingServicio
		extends
			ProductSearcherWeb<DonGasparEntidad> {

	@Override
	protected boolean esDocumentValido(Document document) {
		return false;
	}

	@Override
	protected ExternalProduct obtenerProductosAPartirDeElements(
			Element elementProducto) {
		new ArrayList<>();
		String id = elementProducto.attr("id").toString();
		double precio;
		try {
			precio = Double.parseDouble(elementProducto.select(".precio-box")
					.text().replace("$", ""));
		} catch (Exception e) {
			precio = 0;
		}
		String descripcion = elementProducto.select(".dfloat-left").text();

		// return DonGasparEntidad.builder().id(id)
		// .distribuidoraCodigo(getDistribuidoraCodigo())
		// .nombreProducto(descripcion).precio(precio).build();
		return null;
	}

	@Override
	protected Elements filtrarElementos(Document documento) {
		return documento.getElementsByClass("producto");
	}

	@Override
	public void setCodes() {
		setDistribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_DON_GASPAR);
	}
}
