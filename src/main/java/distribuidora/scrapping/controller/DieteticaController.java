package distribuidora.scrapping.controller;

import distribuidora.scrapping.entities.PeticionFrontEndDocumento;
import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.services.ActualizacionDeDocumentoServicio;
import distribuidora.scrapping.services.RecolectorDeProductosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping(value = "scrapping")
public class DieteticaController {

    @Autowired
    RecolectorDeProductosServicio recolectorDeProductosServicio;

    @Autowired
    ActualizacionDeDocumentoServicio actualizacionDeDocumentoServicio;

    @GetMapping("productos")
    public Collection<Producto> obtenerTodosLosProductos(@RequestParam(name = "busqueda") String busqueda) throws IOException {
        return recolectorDeProductosServicio.obtenerTodosLosProductos(busqueda);
    }

    @PostMapping("recibir-excels")
    public void recibirDocumento(@ModelAttribute PeticionFrontEndDocumento documento) throws IOException {
        actualizacionDeDocumentoServicio.recibirDocumento(documento);
    }

}
