package distribuidora.scrapping.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import distribuidora.scrapping.dto.ExternalProductDto;
import distribuidora.scrapping.entities.ExternalProduct;
import distribuidora.scrapping.services.ExternalProductService;

/**
 * De momento solo se encarga de las busquedas sobre las bases de datos.
 */
@RestController
@RequestMapping(value = "/scrapping")
public class DieteticaController {

    @Autowired
    ExternalProductService productoService;

    /**
     * Metodo para hacer busquedas en funcion a un termino de busqueda
     * @param busqueda Solo tiene en cuenta la descripcion. Puede ser nulo para traer todos los datos.
     * @return Devuelve una collecion de Productos, cada uno tiene una descripcion y precio por cierta cantidad especifica.
     * @see ExternalProduct
     * @throws IOException
     */
	@GetMapping("productos")
	public List<ExternalProductDto> obtenerTodosLosProductos(
			@RequestParam(name = "busqueda") String busqueda)
			throws IOException {
		return productoService.getBySearch(busqueda);
	}

}
