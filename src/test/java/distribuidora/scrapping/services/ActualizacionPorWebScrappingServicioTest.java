package distribuidora.scrapping.services;

import distribuidora.scrapping.configs.Constantes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ActualizacionPorWebScrappingServicioTest {
    @Autowired
    ActualizacionPorWebScrappingServicio actualizacionPorWebScrappingServicio;

//    @Test
//    void actualizarTodasLasDistribuidoras() {
//        actualizacionPorWebScrappingServicio.actualizarTodasLasDistribuidoras();
//    }

    @Test
    void actualizarIndividual(){
        actualizacionPorWebScrappingServicio.actualizarPorDistribuidora(Constantes.LV_DISTRIBUIDORA_DON_GASPAR);
    }
}