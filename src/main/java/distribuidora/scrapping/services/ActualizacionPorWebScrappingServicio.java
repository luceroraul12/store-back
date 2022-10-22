package distribuidora.scrapping.services;

import distribuidora.scrapping.comunicadores.Comunicador;
import distribuidora.scrapping.services.webscrapping.DonGasparWebScrappingServicio;
import distribuidora.scrapping.services.webscrapping.LaGranjaDelCentroWebScrappingServicio;
import distribuidora.scrapping.services.webscrapping.MelarSeleniumWebScrappingServicio;
import distribuidora.scrapping.services.webscrapping.SudamerikWebScrappingServicio;
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
        comunicador.getDisparadorActualizacionWebScrapping().onNext(true);
    }
}
