package distribuidora.scrapping.controller;

import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.services.RecolectorDeProductosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping(value = "scrapping")
public class DieteticaController {

    @Autowired
    RecolectorDeProductosServicio recolectorDeProductosServicio;

    @GetMapping("productos")
    public Collection<Producto> obtenerTodosLosProductos(@RequestParam(name = "busqueda") String busqueda) throws IOException {
        return recolectorDeProductosServicio.obtenerTodosLosProductos(busqueda);
    }

}
