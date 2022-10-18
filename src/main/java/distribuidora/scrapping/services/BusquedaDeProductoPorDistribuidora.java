package distribuidora.scrapping.services;

import distribuidora.scrapping.entities.UnionEntidad;
import distribuidora.scrapping.enums.Distribuidora;
import distribuidora.scrapping.repositories.UnionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

public abstract class BusquedaDeProductoPorDistribuidora<Entidad> {

    protected Distribuidora distribuidora;

    @Autowired
    UnionRepository<Entidad> unionRepository;

    /**
     * Obtiene todos los productos almacenados en la base de datos en funcion a la distribuidora
     */
    public UnionEntidad<Entidad> obtenerProductos(){
        return unionRepository.findByDistribuidora(distribuidora);
    };

    abstract void buscarProductos();

    /**
     * Para almacenar productos en la base de datos
     * @param productos los productos a almacenar
     */
    protected void almacenarProductosEnBaseDeDatos(List<Entidad> productos) {
        UnionEntidad<Entidad> unionEntidad = new UnionEntidad<>();
        unionEntidad.setDatos(productos);
        unionEntidad.setDistribuidora(distribuidora);
        unionEntidad.setFechaScrap(LocalDate.now());
        unionRepository.save(unionEntidad);
    }

    /**
     * Elimina los datos almacenados de cierta distribuidora y vuelve a guardar con datos que deben ser nuevos
     * @param productos
     */
    protected void actualizarProductosEnBaseDeDatos(List<Entidad> productos){
        unionRepository.deleteUnionEntidadByDistribuidora(distribuidora);
        almacenarProductosEnBaseDeDatos(productos);
    }

}
