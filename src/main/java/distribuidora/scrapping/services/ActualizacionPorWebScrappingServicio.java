package distribuidora.scrapping.services;

import distribuidora.scrapping.comunicadores.Comunicador;
import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.PeticionWebScrapping;
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
                .distribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_TODAS)
                .build();
        comunicador.getDisparadorActualizacion().onNext(peticion);
    }

    /**
     * Emite una distribuidora a la que se quiera actualizar.
     * Condicion: que exista un bean de dicho servicio a actualizar.
     * @param distribuidoraCodigo
     */
    public void actualizarPorDistribuidora(String distribuidoraCodigo){
        PeticionWebScrapping peticion = PeticionWebScrapping.builder()
                .distribuidoraCodigo(distribuidoraCodigo)
                .build();
        comunicador.getDisparadorActualizacion().onNext(
                peticion
        );
    }
}
