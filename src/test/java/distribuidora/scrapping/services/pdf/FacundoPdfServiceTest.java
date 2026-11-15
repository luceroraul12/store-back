package distribuidora.scrapping.services.pdf;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import distribuidora.scrapping.configs.Constants;
import distribuidora.scrapping.entities.DatosDistribuidora;
import distribuidora.scrapping.entities.ExternalProduct;
import distribuidora.scrapping.entities.UpdateRequest;

public class FacundoPdfServiceTest {
	@Test
	void testAdquirirProductosDesdePdfMock() throws IOException {
		// 1. Apuntar al archivo PDF ubicado en
		// src/test/resources/static/bernal-update.pdf
		ClassPathResource resource = new ClassPathResource("static/bernal-update.pdf");

		// 2. Leer el archivo como un InputStream y crear el MockMultipartFile
		MultipartFile multipartFile;
		try (InputStream inputStream = resource.getInputStream()) {
			multipartFile = new MockMultipartFile("multipartFiles", // Nombre del parámetro (debe coincidir con el que
																	// espera tu backend)
					"bernal-update.pdf", // Nombre original del archivo
					"application/pdf", // Tipo MIME del archivo
					inputStream // Contenido en bytes (InputStream)
			);
		}

		// 3. Preparar el Request que consume tu método
		UpdateRequest request = new UpdateRequest(Constants.LV_DISTRIBUIDORA_FACUNDO,
				new MultipartFile[] { multipartFile });

		// 4. Instanciar tu servicio y ejecutar el método a probar
		FacundoPdfService service = new FacundoPdfService();
		DatosDistribuidora datos = new DatosDistribuidora(); // Tus datos de prueba necesarios

		List<ExternalProduct> productos = service.adquirirProductosEntidad(request, datos);

		// 5. Validar los resultados
		assertNotNull(productos);
		assertFalse(productos.isEmpty(), "La lista de productos no debería estar vacía");
	}
}
