package distribuidora.scrapping.services.webscrappingconcurrent;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SudamerikWebScrappingConcurrentServicioTest {

    SudamerikWebScrappingConcurrentServicio servicio = new SudamerikWebScrappingConcurrentServicio();

    @Test
    void generarUltimoIndicePaginador() throws IOException {
        int resultado = servicio.generarUltimoIndicePaginador();
        assertEquals(48, resultado);
    }

    @Test
    void generarDocumentos() throws IOException {
        servicio.initImplementacion();
        List<Document> documentos = servicio.generarDocumentos();
        assertEquals(48, documentos.size());
    }
}