package distribuidora.scrapping.services;

import distribuidora.scrapping.entities.Producto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Servicio encargado del filtrado de productos.
 * Trabaja en funcion a un conjunto de productos y un termino de busqueda.
 */
@Service
public class BuscadorPorMedioDeTerminoServicio {

    /**
     * Filtra un conjunto de productos en funcion a un termino de busqueda.
     * @param productos conjunto de productos a los que se realizara el filtrado.
     * @param busqueda termino de busqueda.
     * @return conjunto de productos que pasaron el filtrado.
     */
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

    /**
     * Verifica si es producto valido.
     * Toma un producto y un patron regex y verifica si la descripcion del producto es valida
     * @param p producto a evaluar
     * @param patron patron de busqueda para utilizar
     * @return booleano que indica si ha pasado la validacion
     */
    private boolean esProductoValido(Producto p, Pattern patron) {
        return patron
                .matcher(p.getDescripcion())
                .find();
    }

    /**
     * Encargado de crear el patron regex.
     * Toma un un string spliteado y crea el patron
     * @param partesDeBusqueda termino de busqueda spliteado
     * @return patron regex
     */
    private Pattern crearPatronRegex(String[] partesDeBusqueda) {
        StringBuilder expresion = new StringBuilder();

        expresion.append(".*");
        for (String parte : partesDeBusqueda) {
              expresion.append(parte).append(".*");
        }

        return Pattern.compile(String.valueOf(expresion), Pattern.CASE_INSENSITIVE);
    }

}
