package distribuidora.scrapping.controller;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import distribuidora.scrapping.entities.ExternalProduct;
import distribuidora.scrapping.services.BuscadorPorMedioDeTerminoServicio;

/**
 * De momento solo se encarga de las busquedas sobre las bases de datos.
 */
@RestController
@RequestMapping(value = "/scrapping")
public class DieteticaController {

    @Autowired
    BuscadorPorMedioDeTerminoServicio buscador;

    /**
     * Metodo para hacer busquedas en funcion a un termino de busqueda
     * @param busqueda Solo tiene en cuenta la descripcion. Puede ser nulo para traer todos los datos.
     * @return Devuelve una collecion de Productos, cada uno tiene una descripcion y precio por cierta cantidad especifica.
     * @see ExternalProduct
     * @throws IOException
     */
    @GetMapping("productos")
    public Collection<ExternalProduct> obtenerTodosLosProductos(@RequestParam(name = "busqueda") String busqueda) throws IOException {
//        return repository.buscarConTermino(busqueda);
        return buscador.filtrarProductos(busqueda);
//        return recolectorDeProductosServicio.obtenerTodosLosProductos(busqueda);
    }

}
