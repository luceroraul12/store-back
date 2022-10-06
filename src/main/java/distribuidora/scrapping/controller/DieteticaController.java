package distribuidora.scrapping.controller;

import distribuidora.scrapping.entities.LaGranjaDelCentroEntidad;
import distribuidora.scrapping.entities.MelarEntidad;
import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.entities.SudamerikEntidad;
import distribuidora.scrapping.services.LaGranjaDelCentroServicio;
import distribuidora.scrapping.services.MelarSeleniumServicio;
import distribuidora.scrapping.services.RecolectorDeProductosServicio;
import distribuidora.scrapping.services.SudamerikServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "scrapping")
public class DieteticaController {

    @Autowired
    RecolectorDeProductosServicio recolectorDeProductosServicio;

    @GetMapping("productos")
    public Collection<Producto> obtenerTodosLosProductos() throws IOException {
        return recolectorDeProductosServicio.obtenerTodosLosProductos();
    }

}
