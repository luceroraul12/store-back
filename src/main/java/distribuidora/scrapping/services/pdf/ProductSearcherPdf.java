package distribuidora.scrapping.services.pdf;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.web.multipart.MultipartFile;

import distribuidora.scrapping.entities.DatosDistribuidora;
import distribuidora.scrapping.entities.ExternalProduct;
import distribuidora.scrapping.entities.UpdateRequest;
import distribuidora.scrapping.services.ProductSearcher;
import technology.tabula.ObjectExtractor;
import technology.tabula.Page;
import technology.tabula.PageIterator;
import technology.tabula.RectangularTextContainer;
import technology.tabula.Table;
import technology.tabula.extractors.BasicExtractionAlgorithm;

/**
 * Va a leer linea por linea y luego cada implementación tiene que indicar cómo
 */
public abstract class ProductSearcherPdf extends ProductSearcher {

	@Override
	protected List<ExternalProduct> adquirirProductosEntidad(UpdateRequest request, DatosDistribuidora data)
			throws IOException {

		List<ExternalProduct> externalProducts = new ArrayList<>();

		if (request.getMultipartFiles() == null || request.getMultipartFiles().length == 0) {
			throw new IllegalArgumentException("No se proporcionaron archivos PDF para procesar.");
		}

		// Iteramos sobre cada archivo PDF enviado en el Multipart
		for (MultipartFile multiPart : request.getMultipartFiles()) {

			// Validar que no esté vacío y que sea un PDF (opcional pero recomendado)
			if (multiPart.isEmpty()) {
				continue;
			}

			// Usamos try-with-resources para manejar el InputStream del MultipartFile y el
			// PDDocument
			try (InputStream inputStream = multiPart.getInputStream();
					PDDocument document = PDDocument.load(inputStream)) {

				ObjectExtractor extractor = new ObjectExtractor(document);
				PageIterator pageIterator = extractor.extract();
				BasicExtractionAlgorithm algorithm = new BasicExtractionAlgorithm();
				List<String> rows = new ArrayList<>();
				while (pageIterator.hasNext()) {
					Page page = pageIterator.next();
					List<Table> tables = algorithm.extract(page);

					for (Table table : tables) {
						for (List<RectangularTextContainer> row : table.getRows()) {

							StringBuilder filaStr = new StringBuilder();
							for (RectangularTextContainer cell : row) {
								String textoLimpio = cell.getText().replace("\r", " ").replace("\n", " ").trim();

								filaStr.append(textoLimpio).append(";");
							}

							// Me fijo que sea válido el row para continuar
							if (!isValidRow(filaStr.toString()))
								continue;

							// En caso de que alguien necesite las filas anteriores lo paso.
							String currentRow = filaStr.toString();
							rows.add(currentRow);
							ExternalProduct prod = convertToExternalProduct(currentRow, rows);
							// En caso de validaciones múltiples, solo agrego si no es nulo
							if (prod != null)
								externalProducts.add(prod);
						}
					}
				}

			} catch (Exception e) {
				// Manejar o relanzar la excepción según tu arquitectura
				throw new RuntimeException("Error al procesar el archivo PDF: " + multiPart.getOriginalFilename(), e);
			}
		}

		return externalProducts;
	}

	abstract boolean isValidRow(String row);

	abstract ExternalProduct convertToExternalProduct(String currentRow, List<String> rows);

}
