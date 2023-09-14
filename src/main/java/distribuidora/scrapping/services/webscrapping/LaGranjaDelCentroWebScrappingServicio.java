package distribuidora.scrapping.services.webscrapping;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.productos.especificos.LaGranjaDelCentroEntidad;
import distribuidora.scrapping.services.webscrappingconcurrent.WebScrappingConcurrent;
import distribuidora.scrapping.util.LaGranjaDelCentroUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LaGranjaDelCentroWebScrappingServicio
		extends
			WebScrappingConcurrent<LaGranjaDelCentroEntidad> {

	@Autowired
	LaGranjaDelCentroUtil laGranjaDelCentroUtil;

	/**
	 * Para este caso, contienen un apartado de paginador. En el template
	 * aparece la siguiente etiqueta 'span' con los siguientes atributos:<br>
	 * span class="p-activo">123 <br>
	 * Esta etiqueta solo esta presente cuando es una pagina valida. Por lo
	 * tanto voy a condicionar en funcion a eso.
	 * 
	 * @param document
	 *            template de la pagina Web siguiente
	 * @return
	 */
	@Override
	protected boolean esDocumentValido(Document document) {
		boolean esValido = false;
		for (Element element : document.getElementsByTag("span")) {
			if (element.hasClass("p-activo")) {
				esValido = true;
			}
		}

		return esValido;
	}

	@Override
	protected LaGranjaDelCentroEntidad obtenerProductosAPartirDeElements(
			Element elementProducto) {
		return LaGranjaDelCentroEntidad.builder()
				.distribuidoraCodigo(getDistribuidoraCodigo())
				.nombreProducto(elementProducto
						.getElementsByClass("h3-content-1").text())
				.precio(Double.valueOf(elementProducto
						.getElementsByClass("p-precio-content-1").text()
						.replaceAll("[$.]", "").replaceAll(",", ".")))
				.build();
	}

	@Override
	protected Elements filtrarElementos(Document documento) {
		return documento.select("div.box-content-1");
	}

	@Override
	protected void initImplementacion() {
		setDistribuidoraCodigo(
				Constantes.LV_DISTRIBUIDORA_LA_GRANJA_DEL_CENTRO);
		setEsBuscadorConPaginador(true);
		setUrlBuscador(
				"https://lagranjadelcentro.com.ar/productos.php?pagina=");
	}

	@Override
	protected int generarUltimoIndicePaginador() throws IOException {
		Document document = Jsoup.connect(getUrlBuscador()).get();
		Elements element = document.select(".paginador > .p");
		String url = element.get(1).attr("href");
		return Integer.parseInt(url.split("=")[1]);
	}
}
