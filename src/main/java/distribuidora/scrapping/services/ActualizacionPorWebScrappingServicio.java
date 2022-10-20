package distribuidora.scrapping.services;

import distribuidora.scrapping.services.webscrapping.DonGasparWebScrappingServicio;
import distribuidora.scrapping.services.webscrapping.MelarSeleniumWebScrappingServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActualizacionPorWebScrappingServicio {

    @Autowired
    MelarSeleniumWebScrappingServicio melarSeleniumWebScrappingServicio;

    @Autowired
    DonGasparWebScrappingServicio donGasparWebScrappingServicio;

    public void actualizarTodasLasDistribuidoras(){
        this.donGasparWebScrappingServicio.generarProductosEntidadYActualizarCollecciones(false);
        this.melarSeleniumWebScrappingServicio.generarProductosEntidadYActualizarCollecciones(false);
    }
}
