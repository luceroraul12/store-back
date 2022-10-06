package distribuidora.scrapping.services;

import distribuidora.scrapping.entities.Producto;
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

    public Collection<Producto> obtenerTodosLosProductos() throws IOException {
        Collection<Producto> resultado = new ArrayList<>();

        resultado.addAll(melarSeleniumServicio.getProductosRecolectados());
        resultado.addAll(sudamerikServicio.getProductosRecolectados());
        resultado.addAll(laGranjaDelCentroServicio.getProductosRecolectados());


        return resultado;
    }
}
