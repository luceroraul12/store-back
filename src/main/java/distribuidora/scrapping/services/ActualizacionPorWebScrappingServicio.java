package distribuidora.scrapping.services;

import distribuidora.scrapping.comunicadores.Comunicador;
import distribuidora.scrapping.entities.Peticion;
import distribuidora.scrapping.entities.PeticionWebScrapping;
import distribuidora.scrapping.enums.Distribuidora;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActualizacionPorWebScrappingServicio {
    @Autowired
    Comunicador comunicador;


    public void actualizarTodasLasDistribuidoras(){
        PeticionWebScrapping peticion = PeticionWebScrapping.builder()
                .distribuidora(Distribuidora.TODAS)
                .build();
        comunicador.getDisparadorActualizacion().onNext(peticion);
    }

    public void actualizarPorDistribuidora(Distribuidora distribuidora){
        PeticionWebScrapping peticion = PeticionWebScrapping.builder()
                .distribuidora(distribuidora)
                .build();
        comunicador.getDisparadorActualizacion().onNext(
                peticion
        );
    }
}
