package distribuidora.scrapping.services;

import distribuidora.scrapping.entities.Producto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class BuscadorServicio {

    public Collection<Producto> filtrarProductos(Collection<Producto> productos, String busqueda){
        Collection<Producto> productosFiltrados = new ArrayList<>();
        String[] partesDeBusqueda = busqueda.split(" ");
        Pattern patron = crearPatronRegex(partesDeBusqueda);



        productosFiltrados = productos.stream()
                .filter(
                    p -> esProductoValido(p, patron)
                )
                .collect(Collectors.toList());

        return productosFiltrados;
    }

    private boolean esProductoValido(Producto p, Pattern patron) {
        return patron
                .matcher(p.getDescripcion())
                .find();
    }

    private Pattern crearPatronRegex(String[] partesDeBusqueda) {
        StringBuilder expresion = new StringBuilder();

        expresion.append(".*");
        for (String parte : partesDeBusqueda) {
              expresion.append(parte).append(".*");
        }

        return Pattern.compile(String.valueOf(expresion), Pattern.CASE_INSENSITIVE);
    }

}
