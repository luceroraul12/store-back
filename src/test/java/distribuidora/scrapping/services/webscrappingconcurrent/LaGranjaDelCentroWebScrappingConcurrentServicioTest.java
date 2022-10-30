package distribuidora.scrapping.services.webscrappingconcurrent;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LaGranjaDelCentroWebScrappingConcurrentServicioTest {

    LaGranjaDelCentroWebScrappingConcurrentServicio servicio = new LaGranjaDelCentroWebScrappingConcurrentServicio();

    @Test
    void pruebaInicial() throws IOException {
        servicio.initImplementacion();
        int resultado = servicio.generarUltimoIndicePaginador();

        assertEquals(122, resultado);
    }

    @Test
    void pruebaGenerarDocumentos() throws IOException {
        servicio.initImplementacion();
        int resultado = servicio.generarDocumentos().size();

        assertEquals(122, resultado);
    }

}