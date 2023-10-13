package distribuidora.scrapping.services.webscrapping;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import distribuidora.scrapping.entities.productos.especificos.DonGasparEntidad;
import distribuidora.scrapping.util.DonGasparUtil;

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

        int tries = 3;
        boolean existDocument = false;
        int index = 0;
        while (!existDocument & index != tries){
            document = Jsoup.connect("https://pidorapido.com/dongasparsj")
                    .timeout(0)
                    .execute()
                    .parse();
            if (document == null){
                index++;
            } else {
                existDocument = true;
            }
        }


    }

    @Test
    void obtenerProductosPorDocument() {
        List<DonGasparEntidad> productos = servicio.obtenerProductosPorDocument(document);
        assertEquals(368, productos.size());
    }
}