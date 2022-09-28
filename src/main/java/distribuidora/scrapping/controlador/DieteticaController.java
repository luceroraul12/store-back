package distribuidora.scrapping.controlador;

import distribuidora.scrapping.entidad.MelarEntidad;
import distribuidora.scrapping.servicios.MelarSeleniumServicio;
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

    @GetMapping("melar")
    public List<MelarEntidad> getMelarProductos() throws IOException {
        return melarSeleniumServicio.getProductosRecolectados();
    }
}
