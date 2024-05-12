package distribuidora.scrapping.services.webscrapping;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import distribuidora.scrapping.entities.ExternalProduct;
import distribuidora.scrapping.entities.productos.especificos.FacundoEntidad;
import distribuidora.scrapping.util.FacundoUtil;

@ExtendWith(MockitoExtension.class)
class FacundoRenovadoWebScrappingServicioTest {
    @InjectMocks
    FacundoRenovadoWebScrappingServicio servicio;

    @Spy
    FacundoUtil facundoUtil;

    Document document;

    @BeforeEach
    void seting() throws IOException {
        File file = new File("src/main/resources/static/facundo.html");
        document = Jsoup.parse(file);
    }

    @Test
    void pruebaIds(){
        List<FacundoEntidad> productos = servicio.obtenerProductosPorDocument(document);
        Map<Integer, Double> mapProductos = facundoUtil.arregloToProducto(productos).stream()
                .collect(Collectors.toMap(ExternalProduct::getId, ExternalProduct::getPrice));
        assertEquals(2200, mapProductos.get("482"));
        assertEquals(7500, mapProductos.get("519"));
        assertEquals(200, mapProductos.get("237"));
    }
}