package distribuidora.scrapping.services.webscrapping;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.PeticionWebScrapping;
import distribuidora.scrapping.entities.productos.especificos.FacundoEntidad;
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
        List<FacundoEntidad> resultados = servicio
                .adquirirProductosEntidad(PeticionWebScrapping.builder().distribuidoraCodigo(
                        Constantes.LV_DISTRIBUIDORA_FACUNDO).build());
        assertEquals(941, resultados.size());
    }
}