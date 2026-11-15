package distribuidora.scrapping.services;

import java.util.List;

public interface PdfExtractorService {

	void exportarPdfAArchivo(String path, String rutaArchivoSalida, List<String> excludeRows,
			Integer excludeInitialRows);

}
