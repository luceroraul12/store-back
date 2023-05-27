package distribuidora.scrapping.services.webscrapping;

import distribuidora.scrapping.entities.productos.especificos.DonGasparEntidad;
import distribuidora.scrapping.util.DonGasparUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DonGasparWebScrappingServicioTest {

    @InjectMocks
    DonGasparWebScrappingServicio servicio;

    @Spy
    DonGasparUtil donGasparUtil;

    Document document;

    @BeforeEach
    void setUp() throws IOException {
        servicio.initImplementacion();

        File file = new File("src/main/resources/static/don-gaspar.html");
        document = Jsoup.parse(file);

    }

    @Test
    void obtenerProductosPorDocument() {
        List<DonGasparEntidad> productos = servicio.obtenerProductosPorDocument(document);
        assertEquals(373, productos.size());
    }
}