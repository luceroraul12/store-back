package distribuidora.scrapping.controller;

import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.repositories.ProductoRepository;
import distribuidora.scrapping.services.BuscadorPorMedioDeTerminoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;

/**
 * De momento solo se encarga de las busquedas sobre las bases de datos.
 */
@RestController
@RequestMapping(value = "/scrapping-0.0.1-SNAPSHOT/scrapping")
public class DieteticaController {

    @Autowired
    BuscadorPorMedioDeTerminoServicio buscador;

    @Autowired
    ProductoRepository repository;

    /**
     * Metodo para hacer busquedas en funcion a un termino de busqueda
     * @param busqueda Solo tiene en cuenta la descripcion. Puede ser nulo para traer todos los datos.
     * @return Devuelve una collecion de Productos, cada uno tiene una descripcion y precio por cierta cantidad especifica.
     * @see Producto
     * @throws IOException
     */
    @GetMapping("productos")
    public Collection<Producto> obtenerTodosLosProductos(@RequestParam(name = "busqueda") String busqueda) throws IOException {
//        return repository.buscarConTermino(busqueda);
        return buscador.filtrarProductos(busqueda);
//        return recolectorDeProductosServicio.obtenerTodosLosProductos(busqueda);
    }

}
