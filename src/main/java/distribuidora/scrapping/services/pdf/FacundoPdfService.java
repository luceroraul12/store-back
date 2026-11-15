package distribuidora.scrapping.services.pdf;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import distribuidora.scrapping.configs.Constants;
import distribuidora.scrapping.entities.ExternalProduct;

public class FacundoPdfService extends ProductSearcherPdf {
	
	private static final Pattern PATTERN_CATEGORIA = Pattern.compile("^(.+?);\\s*CATEGORÍA;?$");
    private static final Pattern PATTERN_PRODUCTO  = Pattern.compile("^(.+?)\\s+(\\$\\s*[\\d.,]+)(.*?);*$");

	@Override
	protected boolean isValidRow(String row) {
		// 1. Que no esté vacia
		// 2. Patrón de Categoria
		// Definimos la expresión regular
		Matcher matcherCategory = PATTERN_CATEGORIA.matcher(row);
		Matcher matcherProduct = PATTERN_PRODUCTO.matcher(row);

		return matcherCategory.find() || matcherProduct.find();
	}

	@Override
	protected ExternalProduct convertToExternalProduct(String currentRow, List<String> rows) {
		ExternalProduct product = null;
		// 1. Las categorías no son productos
		if(PATTERN_CATEGORIA.matcher(currentRow).find()) 
			return null;
		
		// 2. Si es un producto, lo parseo
		if(PATTERN_PRODUCTO.matcher(currentRow).find()) {
			Matcher matcher = PATTERN_PRODUCTO.matcher(currentRow);
			if (matcher.find()) {
				String nombre = matcher.group(1).trim();
				String precioStr = matcher.group(2).replace("$", "").replace(",", "").trim();
				String reguladorStr = matcher.group(3).trim();

				Double precio = Double.parseDouble(precioStr);
				Double regulador = 0.0;
				if (!reguladorStr.isEmpty()) {
					try {
						regulador = Double.parseDouble(reguladorStr);
					} catch (NumberFormatException e) {
						// Si no se puede parsear, dejamos el regulador en 0.0
						regulador = 0.0;
					}
				}

				product = new ExternalProduct();
				product.setCode(currentRow);
				product.setTitle(currentRow);
				product.setPrice(regulador);
			}
		}

		return product;
	}

	@Override
	public void setCodes() {
		setDistribuidoraCodigo(Constants.LV_DISTRIBUIDORA_FACUNDO);
	}

}
