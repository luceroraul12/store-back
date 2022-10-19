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

@Service
public class RecolectorDeProductosServicio {

    @Autowired
    SudamerikWebScrappingServicio sudamerikWebScrappingServicio;

    @Autowired
    LaGranjaDelCentroWebScrappingServicio laGranjaDelCentroWebScrappingServicio;

    @Autowired
    MelarSeleniumWebScrappingServicio melarSeleniumWebScrappingServicio;

    @Autowired
    DonGasparWebScrappingServicio donGasparWebScrappingServicio;

    @Autowired
    BuscadorServicio buscadorServicio;

    public Collection<Producto> obtenerTodosLosProductos(String busqueda) throws IOException {
        Collection<Producto> conjuntoDeProductos = new ArrayList<>();

        conjuntoDeProductos.addAll(melarSeleniumWebScrappingServicio.getProductosRecolectados());
        conjuntoDeProductos.addAll(sudamerikWebScrappingServicio.getProductosRecolectados());
        conjuntoDeProductos.addAll(laGranjaDelCentroWebScrappingServicio.getProductosRecolectados());
        conjuntoDeProductos.addAll(donGasparWebScrappingServicio.getProductosRecolectados());


        return buscadorServicio.filtrarProductos(conjuntoDeProductos, busqueda);
    }
}
