package distribuidora.scrapping.services.webscrapping;

import distribuidora.scrapping.entities.productos.especificos.SudamerikEntidad;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
    void obtenerProductosAPartirDeElements() throws IOException {
        Document docBueno = Jsoup.parse(new File("src/main/resources/static/sudamerik.html"));
        Elements cantidadDeProductosObtenidos = servicio.filtrarElementos(docBueno);
        List<SudamerikEntidad> productosCreados = servicio.obtenerProductosAPartirDeElements(cantidadDeProductosObtenidos);

        assertEquals(9, productosCreados.size());

    }

    @Test
    void filtrarElementos() throws IOException {
        Document docBueno = Jsoup.parse(new File("src/main/resources/static/sudamerik.html"));
        Elements cantidadDeProductosObtenidos = servicio.filtrarElementos(docBueno);

        assertEquals(9,cantidadDeProductosObtenidos.size());
    }

    @Test
    void mapearEntidadaProducto() {
    }
}