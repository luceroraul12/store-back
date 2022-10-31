package distribuidora.scrapping.services;

import distribuidora.scrapping.enums.Distribuidora;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Interface que contiene las acciones generales que se realizan con las bases de datos
 * @param <Entidad> Tipo del elemento a manipular
 */
public interface AccionesBaseDeDatos<Entidad> {

    void actualizarDatos(List<Entidad> datos);
    void guardarDatos(List<Entidad> datos);
    void eliminarDatos(Distribuidora distribuidora);
}
