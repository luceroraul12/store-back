package distribuidora.scrapping.services.webscrappingconcurrent;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LaGranjaDelCentroWebScrappingConcurrentServicioTest {

    @Autowired
    LaGranjaDelCentroWebScrappingConcurrentServicio servicio;

    @Test
    void pruebaInicial() throws IOException {
        int resultado = servicio.generarUltimoIndicePaginador();

        assertEquals(122, resultado);
    }

    @Test
    void pruebaGenerarDocumentos() throws IOException {
        int resultado = servicio.generarDocumentos().size();

        assertEquals(122, resultado);
    }

}