package distribuidora.scrapping.services;

import distribuidora.scrapping.comunicadores.Comunicador;
import distribuidora.scrapping.entities.Peticion;
import distribuidora.scrapping.entities.PeticionWebScrapping;
import distribuidora.scrapping.enums.Distribuidora;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActualizacionPorWebScrappingServicio {
//    @Autowired
//    SudamerikWebScrappingServicio sudamerikWebScrappingServicio;
//
//    @Autowired
//    MelarSeleniumWebScrappingServicio melarSeleniumWebScrappingServicio;
//
//    @Autowired
//    DonGasparWebScrappingServicio donGasparWebScrappingServicio;
//
//    @Autowired
//    LaGranjaDelCentroWebScrappingServicio laGranjaDelCentroWebScrappingServicio;

    @Autowired
    Comunicador comunicador;


    public void actualizarTodasLasDistribuidoras(){
//        this.donGasparWebScrappingServicio.generarProductosEntidadYActualizarCollecciones(false);
//        this.melarSeleniumWebScrappingServicio.generarProductosEntidadYActualizarCollecciones(false);
//        this.laGranjaDelCentroWebScrappingServicio.generarProductosEntidadYActualizarCollecciones(false);
//        this.sudamerikWebScrappingServicio.generarProductosEntidadYActualizarCollecciones(false);
        PeticionWebScrapping peticion = PeticionWebScrapping.builder().build();
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
