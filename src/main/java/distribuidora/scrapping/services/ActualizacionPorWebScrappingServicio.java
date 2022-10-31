package distribuidora.scrapping.services;

import distribuidora.scrapping.comunicadores.Comunicador;
import distribuidora.scrapping.entities.Peticion;
import distribuidora.scrapping.entities.PeticionWebScrapping;
import distribuidora.scrapping.enums.Distribuidora;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio para realizar actualizaciones de buscador por web scrapping de productos en base de datos.
 */
@Service
public class ActualizacionPorWebScrappingServicio {
    @Autowired
    Comunicador comunicador;


    /**
     * Emite una peticion para actualizar todas las distribuidoras;
     * Condicion: Que existan beans del servicio a actualizar.
     */
    public void actualizarTodasLasDistribuidoras(){
        PeticionWebScrapping peticion = PeticionWebScrapping.builder()
                .distribuidora(Distribuidora.TODAS)
                .build();
        comunicador.getDisparadorActualizacion().onNext(peticion);
    }

    /**
     * Emite una distribuidora a la que se quiera actualizar.
     * Condicion: que exista un bean de dicho servicio a actualizar.
     * @param distribuidora
     */
    public void actualizarPorDistribuidora(Distribuidora distribuidora){
        PeticionWebScrapping peticion = PeticionWebScrapping.builder()
                .distribuidora(distribuidora)
                .build();
        comunicador.getDisparadorActualizacion().onNext(
                peticion
        );
    }
}
