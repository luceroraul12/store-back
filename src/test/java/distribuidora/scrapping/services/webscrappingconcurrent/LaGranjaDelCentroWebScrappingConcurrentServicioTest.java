package distribuidora.scrapping.services.webscrappingconcurrent;

import distribuidora.scrapping.entities.PeticionWebScrapping;
import distribuidora.scrapping.entities.productos.especificos.LaGranjaDelCentroEntidad;
import distribuidora.scrapping.enums.Distribuidora;
import distribuidora.scrapping.services.webscrapping.LaGranjaDelCentroWebScrappingServicio;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
class LaGranjaDelCentroWebScrappingConcurrentServicioTest {

    LaGranjaDelCentroWebScrappingConcurrentServicio servicioConcurrent = new LaGranjaDelCentroWebScrappingConcurrentServicio();

    LaGranjaDelCentroWebScrappingServicio servicio = new LaGranjaDelCentroWebScrappingServicio();


    @Test
    void pruebaInicial() throws IOException {
        servicioConcurrent.initImplementacion();
        int resultado = servicioConcurrent.generarUltimoIndicePaginador();

        assertEquals(123, resultado);
    }

    @Test
    void pruebaGenerarDocumentos() throws IOException {
        servicioConcurrent.initImplementacion();
        int maxPaginador = servicioConcurrent.generarUltimoIndicePaginador();
        int resultado = servicioConcurrent.generarDocumentos().size();

        assertEquals(maxPaginador, resultado);
    }

    @Test
    void pruebaGenerarProductosConcurrent() {
        servicioConcurrent.initImplementacion();
        List<LaGranjaDelCentroEntidad> productos = servicioConcurrent
                .adquirirProductosEntidad(new PeticionWebScrapping(Distribuidora.LA_GRANJA_DEL_CENTRO));

        assertEquals(200, productos.size());
    }

    @Test
    void pruebaGenerarProductos() {
        servicioConcurrent.initImplementacion();
        List<LaGranjaDelCentroEntidad> productos = servicioConcurrent
                .adquirirProductosEntidad(new PeticionWebScrapping(Distribuidora.LA_GRANJA_DEL_CENTRO));

        assertEquals(200, productos.size());
    }

}