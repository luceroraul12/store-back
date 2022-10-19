package distribuidora.scrapping.services;

import distribuidora.scrapping.services.webscrapping.MelarSeleniumWebScrappingServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActualizacionPorWebScrappingServicio {

    @Autowired
    MelarSeleniumWebScrappingServicio melarSeleniumWebScrappingServicio;

    public void actualizarTodasLasDistribuidoras(){
        melarSeleniumWebScrappingServicio.gene
        this.melarSeleniumWebScrappingServicio.actualizarProductosEnTodasLasColecciones(
                this.melarSeleniumWebScrappingServicio.generarProductosEntidadYActualizarCollecciones(false)
        );

    }

}
