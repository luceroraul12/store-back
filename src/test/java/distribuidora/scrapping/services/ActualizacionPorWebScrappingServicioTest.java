package distribuidora.scrapping.services;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import distribuidora.scrapping.configs.Constantes;

@SpringBootTest
class ActualizacionPorWebScrappingServicioTest {
    @Autowired
    ActualizacionPorWebScrappingServicio actualizacionPorWebScrappingServicio;

//    @Test
//    void actualizarTodasLasDistribuidoras() {
//        actualizacionPorWebScrappingServicio.actualizarTodasLasDistribuidoras();
//    }

    @Test
    void actualizarIndividual() throws IOException{
        actualizacionPorWebScrappingServicio.update(Constantes.LV_DISTRIBUIDORA_DON_GASPAR);
        actualizacionPorWebScrappingServicio.update(Constantes.LV_DISTRIBUIDORA_FACUNDO);
        actualizacionPorWebScrappingServicio.update(Constantes.LV_DISTRIBUIDORA_LA_GRANJA_DEL_CENTRO);
    }
}