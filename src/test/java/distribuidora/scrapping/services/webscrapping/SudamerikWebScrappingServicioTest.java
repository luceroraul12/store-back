package distribuidora.scrapping.services.webscrapping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SudamerikWebScrappingServicioTest {


    @Autowired
    SudamerikWebScrappingServicio servicio;

    /**
     * Use tres enlaces para probar<br>
     * dentro de rango: 30 <br>
     * al limite: 48 <br>
     * fuera de rango: 22938470
     * @throws IOException
     */
    @Test
    void esDocumentValido() throws IOException {
        Document docBueno = Jsoup
                .connect("https://www.sudamerikargentina.com.ar/productos/pagina/30").get();
        Document docLimite = Jsoup
                .connect("https://www.sudamerikargentina.com.ar/productos/pagina/48").get();
        Document docMalo = Jsoup
                .connect("https://www.sudamerikargentina.com.ar/productos/pagina/22938470").get();

        assertTrue(servicio.esDocumentValido(docBueno));
        assertTrue(servicio.esDocumentValido(docLimite));
        assertFalse(servicio.esDocumentValido(docMalo));

    }

    @Test
    void obtenerProductosAPartirDeElements() {
    }

    @Test
    void filtrarElementos() {
    }

    @Test
    void mapearEntidadaProducto() {
    }
}