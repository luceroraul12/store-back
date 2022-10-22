package distribuidora.scrapping.services;

import distribuidora.scrapping.enums.Distribuidora;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

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
        actualizacionPorWebScrappingServicio.actualizarPorDistribuidora(Distribuidora.DON_GASPAR);
    }
}