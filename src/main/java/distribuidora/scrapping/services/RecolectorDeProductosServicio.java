package distribuidora.scrapping.services;

import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.services.webscrapping.DonGasparService;
import distribuidora.scrapping.services.webscrapping.LaGranjaDelCentroServicio;
import distribuidora.scrapping.services.webscrapping.MelarSeleniumServicio;
import distribuidora.scrapping.services.webscrapping.SudamerikServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class RecolectorDeProductosServicio {

    @Autowired
    SudamerikServicio sudamerikServicio;

    @Autowired
    LaGranjaDelCentroServicio laGranjaDelCentroServicio;

    @Autowired
    MelarSeleniumServicio melarSeleniumServicio;

    @Autowired
    DonGasparService donGasparService;

    @Autowired
    BuscadorServicio buscadorServicio;

    public Collection<Producto> obtenerTodosLosProductos(String busqueda) throws IOException {
        Collection<Producto> conjuntoDeProductos = new ArrayList<>();

        conjuntoDeProductos.addAll(melarSeleniumServicio.getProductosRecolectados());
        conjuntoDeProductos.addAll(sudamerikServicio.getProductosRecolectados());
        conjuntoDeProductos.addAll(laGranjaDelCentroServicio.getProductosRecolectados());
        conjuntoDeProductos.addAll(donGasparService.getProductosRecolectados());


        return buscadorServicio.filtrarProductos(conjuntoDeProductos, busqueda);
    }
}
