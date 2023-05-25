package distribuidora.scrapping.services;

import java.util.List;

/**
 * Interface que contiene las acciones generales que se realizan con las bases de datos
 * @param <Entidad> Tipo del elemento a manipular
 */
public interface AccionesBaseDeDatos<Entidad> {

    void actualizarDatos(List<Entidad> datos);
    void guardarDatos(List<Entidad> datos);
    void eliminarDatos(String distribuidoraCodigo);
}
