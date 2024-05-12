package distribuidora.scrapping.services.webscrappingconcurrent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.PeticionWebScrapping;
import distribuidora.scrapping.entities.productos.especificos.LaGranjaDelCentroEntidad;
import distribuidora.scrapping.services.webscrapping.LaGranjaDelCentroWebScrappingServicio;

//@SpringBootTest
class LaGranjaDelCentroWebScrappingConcurrentServicioTest {

    LaGranjaDelCentroWebScrappingConcurrentServicio servicioConcurrent = new LaGranjaDelCentroWebScrappingConcurrentServicio();

    LaGranjaDelCentroWebScrappingServicio servicio = new LaGranjaDelCentroWebScrappingServicio();


    @Test
    void pruebaInicial() throws IOException {
        int resultado = servicioConcurrent.generarUltimoIndicePaginador();

        assertEquals(123, resultado);
    }

    @Test
    void pruebaGenerarDocumentos() throws IOException {
        int maxPaginador = servicioConcurrent.generarUltimoIndicePaginador();
        int resultado = servicioConcurrent.generarDocumentos().size();

        assertEquals(maxPaginador, resultado);
    }

    @Test
    void pruebaGenerarProductosConcurrent() throws IOException {
        List<LaGranjaDelCentroEntidad> productos = servicioConcurrent
                .adquirirProductosEntidad(new PeticionWebScrapping(Constantes.LV_DISTRIBUIDORA_LA_GRANJA_DEL_CENTRO));

        assertEquals(200, productos.size());
    }

    @Test
    void pruebaGenerarProductos() throws IOException {
        List<LaGranjaDelCentroEntidad> productos = servicioConcurrent
                .adquirirProductosEntidad(new PeticionWebScrapping(Constantes.LV_DISTRIBUIDORA_LA_GRANJA_DEL_CENTRO));

        assertEquals(200, productos.size());
    }

}