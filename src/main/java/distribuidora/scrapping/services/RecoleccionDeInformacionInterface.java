package distribuidora.scrapping.services;

import distribuidora.scrapping.entities.Producto;

import java.util.List;

public interface RecoleccionDeInformacionInterface<Entidad> {

    Producto mapearEntidadaProducto(Entidad productoEntidad);

    List<Producto> convertirTodosAProducto(List<Entidad> productosEntidad);

}
