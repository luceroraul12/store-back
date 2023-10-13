package distribuidora.scrapping.services.webscrappingconcurrent;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import distribuidora.scrapping.entities.productos.especificos.SudamerikEntidad;

//@Service
public class SudamerikWebScrappingConcurrentServicio
		extends
			WebScrappingConcurrent<SudamerikEntidad> {
	@Override
	protected void initImplementacion() {
		setUrlBuscador(
				"https://www.sudamerikargentina.com.ar/productos/pagina/");
	}

	@Override
	protected boolean esDocumentValido(Document document) {
		return false;
	}

	@Override
	protected SudamerikEntidad obtenerProductosAPartirDeElements(
			Element elementProducto) {
		return null;
	}

	@Override
	protected Elements filtrarElementos(Document documento) {
		return null;
	}

	@Override
	protected int generarUltimoIndicePaginador() throws IOException {
		Document document = Jsoup
				.parse(new File("src/main/resources/static/sudamerik.html"));
		Elements elements = document.select(".pagination > li");
		int indiceMaximo = Integer
				.parseInt(elements.get(elements.size() - 2).text());
		return indiceMaximo;
	}
}
