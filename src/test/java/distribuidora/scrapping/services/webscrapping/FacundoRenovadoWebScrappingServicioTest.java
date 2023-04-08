package distribuidora.scrapping.services.webscrapping;

import distribuidora.scrapping.entities.PeticionWebScrapping;
import distribuidora.scrapping.entities.productos.especificos.FacundoEntidad;
import distribuidora.scrapping.enums.Distribuidora;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FacundoRenovadoWebScrappingServicioTest {
    @Autowired
    FacundoRenovadoWebScrappingServicio servicio;

    @Test
    void pruebaDocument(){
        List<FacundoEntidad> resultados = servicio.adquirirProductosEntidad(PeticionWebScrapping.builder().distribuidora(Distribuidora.FACUNDO).build());
        assertEquals(582, resultados.size());
    }
}