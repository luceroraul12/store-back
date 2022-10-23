package distribuidora.scrapping.services;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface AccionesBaseDeDatos<Entidad> {

    void actualizarDatos(List<Entidad> datos);
    void guardarDatos(List<Entidad> datos);
    void eliminarDatos();
}
