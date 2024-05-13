package distribuidora.scrapping.services.webscrapping;

import java.util.Date;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.ExternalProduct;
import distribuidora.scrapping.entities.productos.especificos.FacundoEntidad;

@Service
public class FacundoRenovadoWebScrappingServicio
		extends
			ProductSearcherWeb<FacundoEntidad> {
	@Override
	protected boolean esDocumentValido(Document document) {
		return false;
	}

	@Override
	protected ExternalProduct obtenerProductosAPartirDeElements(
			Element elementProducto) {
		// Extraigo los datos que me parecen importantes del producto
		String code = elementProducto.attr("data-idproducto");
		String name = elementProducto.getElementsByTag("h5").textNodes().get(0)
				.text().replaceAll("_",
						" ")
				.subSequence(0, elementProducto.getElementsByTag("h5")
						.textNodes().get(0).text().length() - 3)
				.toString();

		String category = elementProducto.parent().parent().parent().parent()
				.getElementsByTag("h2").text();

		Double price = Double.valueOf(elementProducto.getElementsByTag("strong")
				.text().replaceAll(" ", "").subSequence(1, elementProducto
						.getElementsByTag("strong").text().length() - 1)
				.toString());

		// Genero el externalProduct
		String observation = String.format("%s - %s", category, name);
		ExternalProduct externalProduct = new ExternalProduct(null, observation,
				price, new Date(), getTipoDistribuidora(), code);

		// return FacundoEntidad.builder().id(id).categoria(nombre)
		// .precioMayor(precio).categoriaRenglon(categoria)
		// .distribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_FACUNDO)
		// .cantidad("").build();
		return externalProduct;
	}

	@Override
	protected Elements filtrarElementos(Document documento) {
		return documento.getElementsByClass("item row pointer");
	}

	@Override
	public void setCodes() {
		setDistribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_FACUNDO);
	}
}
