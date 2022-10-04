package distribuidora.scrapping.controller;

import distribuidora.scrapping.entities.LaGranjaDelCentroEntidad;
import distribuidora.scrapping.entities.MelarEntidad;
import distribuidora.scrapping.entities.SudamerikEntidad;
import distribuidora.scrapping.services.LaGranjaDelCentroServicio;
import distribuidora.scrapping.services.MelarSeleniumServicio;
import distribuidora.scrapping.services.SudamerikServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "scrapping")
public class DieteticaController {

    @Autowired
    MelarSeleniumServicio melarSeleniumServicio;

    @Autowired
    LaGranjaDelCentroServicio granjaServicio;

    @Autowired
    SudamerikServicio sudamerikServicio;

    @GetMapping("melar")
    public List<MelarEntidad> getMelarProductos() throws IOException {
        return melarSeleniumServicio.getProductosRecolectados();
    }

    @GetMapping("granja")
    public List<LaGranjaDelCentroEntidad> getGranjaProductos() throws IOException {
        return granjaServicio.getProductosRecolectados();
    }

    @GetMapping("sudamerik")
    public List<SudamerikEntidad> getSudamerikProductos() throws IOException {
        return sudamerikServicio.getProductosRecolectados();
    }
}
