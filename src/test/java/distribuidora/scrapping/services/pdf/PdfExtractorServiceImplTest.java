package distribuidora.scrapping.services.pdf;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import distribuidora.scrapping.services.PdfExtractorServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PdfExtractorServiceImplTest {

	@InjectMocks
	PdfExtractorServiceImpl service;

	@Test
	void precioNormal() {
		List<String> excludes = Arrays.asList("Catalogo", "Lista Mayorista", "importante",
				"bernal alimentos saludables", "vigencia:", "catálogo  comercial  actualizado",
				"frutos secos, harinas, legumbres, semillas y mucho mas", "lista mayorista", "modalidad vigencia");
		service.exportarPdfAArchivo("static/bernal-update.pdf", "bernal_extraido.csv", null, 5);
	}

}
