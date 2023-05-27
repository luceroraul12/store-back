package distribuidora.scrapping.services.webscrapping;

import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.entities.productos.especificos.MelarEntidad;
import distribuidora.scrapping.util.MelarUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MelarSeleniumWebScrappingServicioTest {

    @InjectMocks
    MelarSeleniumWebScrappingServicio servicio;

    @Spy
    MelarUtil melarUtil;

    Document document;

    @BeforeEach
    void setUp() throws IOException {
        servicio.initImplementacion();

        File file = new File("src/main/resources/static/melar.html");
        document = Jsoup.parse(file);

    }

    @Test
    void pruebaDeId(){
        Map<String, Double> mapProductos = melarUtil.arregloToProducto(servicio.obtenerProductosPorDocument(document)).stream()
                .collect(Collectors.toMap(Producto::getId, Producto::getPrecioPorCantidadEspecifica));
        assertEquals(479.42 * 1, mapProductos.get("15A0208 ARF"));
        assertEquals(1718.70 * 5, mapProductos.get("15A0201 PKF"));
        assertEquals(1505.81 * 25, mapProductos.get("08C0601 ARG"));
    }
}