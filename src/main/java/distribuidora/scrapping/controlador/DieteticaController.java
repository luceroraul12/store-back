package distribuidora.scrapping.controlador;

import distribuidora.scrapping.entidad.LaGranjaDelCentroEntidad;
import distribuidora.scrapping.entidad.MelarEntidad;
import distribuidora.scrapping.entidad.SudamerikEntidad;
import distribuidora.scrapping.servicios.LaGranjaDelCentroServicio;
import distribuidora.scrapping.servicios.MelarSeleniumServicio;
import distribuidora.scrapping.servicios.SudamerikServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("scrapping")
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
