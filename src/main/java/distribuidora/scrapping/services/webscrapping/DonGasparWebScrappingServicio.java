package distribuidora.scrapping.services.webscrapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
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
			ProductSearcherWeb {

	@Override
	protected boolean esDocumentValido(Document document) {
		return false;
	}

	@Override
	protected ExternalProduct obtenerProductosAPartirDeElements(
			Element elementProducto) {
		new ArrayList<>();
		double price;
		try {
			price = Double.parseDouble(elementProducto.select(".precio-box")
					.text().replace("$", ""));
		} catch (Exception e) {
			price = 0;
		}
		String description = elementProducto.select(".dfloat-left").text();
		String code = null;
		Pattern MY_PATTERN = Pattern.compile("\\(([0-9]*)\\)");
		Matcher m = MY_PATTERN.matcher(description);
		while (m.find()) {
		    code = m.group(1);
		}
		if(code == null)
			return null;
		
		ExternalProduct product = null;
		// Si el nombre contiene la palabra oferta, no lo guardo ya que estara repetido
		if(!description.contains("(OFERTA)"))
			product = new ExternalProduct(null, description, price, null,
					getTipoDistribuidora(), code);
		
		return product;
	}

	@Override
	protected Elements filtrarElementos(Document documento) {
		return documento.getElementsByClass("producto");
	}

	@Override
	public void setCodes() {
		setDistribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_DON_GASPAR);
	}

	@Override
	protected int generarUltimoIndicePaginador() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}
}
