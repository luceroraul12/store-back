package distribuidora.scrapping.services;

import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.services.webscrapping.DonGasparWebScrappingServicio;
import distribuidora.scrapping.services.webscrapping.LaGranjaDelCentroWebScrappingServicio;
import distribuidora.scrapping.services.webscrapping.MelarSeleniumWebScrappingServicio;
import distribuidora.scrapping.services.webscrapping.SudamerikWebScrappingServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Servicio encargado de recolectar todos los productos y realizar busqueda.
 * La finalidad es centralizar todos los servicios finales en un unico lugar y luego realizar la busqueda por medio de un servicio auxiliar
 * @see BuscadorPorMedioDeTerminoServicio
 */
@Service
public class RecolectorDeProductosServicio {

//    @Autowired
//    SudamerikWebScrappingServicio sudamerikWebScrappingServicio;
//    @Autowired
//    LaGranjaDelCentroWebScrappingServicio laGranjaDelCentroWebScrappingServicio;
//    @Autowired
//    MelarSeleniumWebScrappingServicio melarSeleniumWebScrappingServicio;
//    @Autowired
//    DonGasparWebScrappingServicio donGasparWebScrappingServicio;

    @Autowired
    ProductoServicio productoServicio;

    @Autowired
    BuscadorPorMedioDeTerminoServicio buscadorPorMedioDeTerminoServicio;

    /**
     * Encargado de buscar productos que contengan el termino de busqueda.
     * Llama a todos los servicios finales, obtiene todos los productos y luego el servicio auxiliar hace el filtrado.
     * @param busqueda termino de busqueda
     * @return
     * @throws IOException
     */
    public Collection<Producto> obtenerTodosLosProductos(String busqueda) throws IOException {
//        Collection<Producto> conjuntoDeProductos = new ArrayList<>();

//        conjuntoDeProductos.addAll(melarSeleniumWebScrappingServicio.getProductosRecolectados());
//        conjuntoDeProductos.addAll(sudamerikWebScrappingServicio.getProductosRecolectados());
//        conjuntoDeProductos.addAll(laGranjaDelCentroWebScrappingServicio.getProductosRecolectados());
//        conjuntoDeProductos.addAll(donGasparWebScrappingServicio.getProductosRecolectados());


        return buscadorPorMedioDeTerminoServicio.filtrarProductos(
                this.productoServicio.obtenerTodosLosProductosAlmacenados(),
                busqueda
        );
    }
}
