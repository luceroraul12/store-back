package distribuidora.scrapping.services;

import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Encargada de la collecion de productos finales.
 */
@Service
public class ProductoServicio {

    @Autowired
    ProductoRepository productoRepository;

    /**
     * Actualiza los productos de cierta distribuidora.
     * Almacena productos, en caso de que hayan existentes, los borra y guarda los nuevos
     * @param productos productos que se quiere almacenar.
     * @param distribuidora distribuidora con la que se quiere trabajar.
     */
    public void actualizarProductosPorDistribuidora(List<Producto> productos, String distribuidoraCodigo){
       eliminarProductosPorDistribuidora(distribuidoraCodigo);
       crearProductosPorDistribuidora(productos);
    }

    public List<Producto> obtenerTodosLosProductosAlmacenados(){
        return this.productoRepository.findAll();
    }
    private void crearProductosPorDistribuidora(List<Producto> productos){
        this.productoRepository.saveAll(productos);
    }
    private void eliminarProductosPorDistribuidora(String distribuidoraCodigo){
        this.productoRepository.deleteAllByDistribuidoraCodigo(distribuidoraCodigo);
    }
    public Producto getProductoByDistribuidoraCodigoAndId(String distribuidoraCodigo, String idReferencia) {
        return this.productoRepository.findByDistribuidoraCodigoAndId(distribuidoraCodigo, idReferencia);
    }
}
