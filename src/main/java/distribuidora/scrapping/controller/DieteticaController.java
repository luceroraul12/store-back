package distribuidora.scrapping.controller;

import distribuidora.scrapping.entities.PeticionFrontEndDocumento;
import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.services.RecolectorDeProductosServicio;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

    @PostMapping("recibir-excels")
    public void recibirDocumento(@ModelAttribute PeticionFrontEndDocumento documento) throws IOException {
        System.out.println(documento.getDistribuidora());
        MultipartFile[] excels = documento.getExcels();

        for (MultipartFile excel : excels) {
            System.out.println(excel.getOriginalFilename());
        }
    }

}
