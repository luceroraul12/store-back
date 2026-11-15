package distribuidora.scrapping.services;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;

import technology.tabula.ObjectExtractor;
import technology.tabula.Page;
import technology.tabula.PageIterator;
import technology.tabula.RectangularTextContainer;
import technology.tabula.Table;
import technology.tabula.extractors.BasicExtractionAlgorithm;

@Service
public class PdfExtractorServiceImpl implements PdfExtractorService {

	/**
	 * Lee el PDF desde resources y guarda la extracción en un archivo de salida.
	 * 
	 * @param rutaEnResources   Ruta del archivo dentro de resources (ej:
	 *                          "static/bernal-update.pdf")
	 * @param rutaArchivoSalida Ruta donde se guardará el resultado (ej:
	 *                          "resultado_bernal.csv" o "resultado_bernal.txt")
	 */
	public void exportarPdfAArchivo(String rutaEnResources, String rutaArchivoSalida, List<String> excludeRows,
			Integer excludeInitialRows) {
		// corrección de exclusión de filas: convertir a minúsculas para comparación
		if (CollectionUtils.isNotEmpty(excludeRows))
			excludeRows = excludeRows.stream().map(e -> e.toLowerCase()).toList();

		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(rutaEnResources);
				FileWriter fw = new FileWriter(rutaArchivoSalida);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {

			if (inputStream == null) {
				throw new FileNotFoundException("No se encontró el archivo en resources: " + rutaEnResources);
			}

			try (PDDocument document = PDDocument.load(inputStream)) {

				ObjectExtractor extractor = new ObjectExtractor(document);
				PageIterator pageIterator = extractor.extract();
				BasicExtractionAlgorithm algorithm = new BasicExtractionAlgorithm();

				int numeroPagina = 1;
				while (pageIterator.hasNext()) {
					Page page = pageIterator.next();
					List<Table> tables = algorithm.extract(page);
					
					int tableCount = 1;
					for (Table table : tables) {
						for (List<RectangularTextContainer> row : table.getRows()) {
							StringBuilder filaStr = new StringBuilder();

							for (RectangularTextContainer cell : row) {
								if (numeroPagina == 1  && tableCount == 1 && excludeInitialRows != null
										&& row.indexOf(cell) < excludeInitialRows) {
									continue; // Saltar las filas iniciales si se especifica
								}
								String textoLimpio = cell.getText().replace("\r", " ").replace("\n", " ")
										.replace(";", ",") // Si usas CSV con separador ';'
										.trim().toLowerCase();

								// 2. Filtrar encabezados institucionales típicos que no son productos
								if (CollectionUtils.isNotEmpty(excludeRows)
										&& excludeRows.stream().anyMatch(exclude -> exclude.contains(textoLimpio))) {
									System.out.println(
											"Descartando fila por coincidencia con excludeRows: " + textoLimpio);
								} else
									filaStr.append(textoLimpio).append(";"); // Separador por punto y coma
							}

							// Guardamos la fila en el archivo
							out.println(filaStr.toString());
						}
						tableCount++;
					}
					numeroPagina++;
				}

				System.out.println(" Extracción completada exitosamente. Archivo guardado en: " + rutaArchivoSalida);
			}

		} catch (Exception e) {
			System.err.println("Error al procesar y guardar el PDF: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
