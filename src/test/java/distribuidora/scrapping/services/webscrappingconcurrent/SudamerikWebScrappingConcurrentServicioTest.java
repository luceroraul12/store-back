package distribuidora.scrapping.services.webscrappingconcurrent;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SudamerikWebScrappingConcurrentServicioTest {

    @Autowired
    SudamerikWebScrappingConcurrentServicio servicio;

    @Test
    void generarUltimoIndicePaginador() throws IOException {
        int resultado = servicio.generarUltimoIndicePaginador();
        assertEquals(48, resultado);
    }

    @Test
    void generarDocumentos() throws IOException {
        List<Document> documentos = servicio.generarDocumentos();
        assertEquals(48, documentos.size());
    }
}