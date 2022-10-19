package distribuidora.scrapping.controller;

import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.services.RecolectorDeProductosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping(value = "scrapping")
/**
 * De momento solo se encarga de las busquedas sobre las bases de datos.
 * @see RecolectorDeProductosServicio
 */
public class DieteticaController {

    @Autowired
    RecolectorDeProductosServicio recolectorDeProductosServicio;

    /**
     * Metodo para hacer busquedas en funcion a un termino de busqueda
     * @param busqueda Solo tiene en cuenta la descripcion. Puede ser nulo para traer todos los datos.
     * @return Devuelve una collecion de Productos, cada uno tiene una descripcion y precio por cierta cantidad especifica
     * @throws IOException
     */
    @GetMapping("productos")
    public Collection<Producto> obtenerTodosLosProductos(@RequestParam(name = "busqueda") String busqueda) throws IOException {
        return recolectorDeProductosServicio.obtenerTodosLosProductos(busqueda);
    }

}
