package distribuidora.scrapping.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Es la entidad con la que llegan al FrontEnd
 */
@Data
@Builder
@Document(collection = "productosCliente")
public class Producto {
    @Id
    private String id;
    private String descripcion;
    private Double precioPorCantidadEspecifica;
    /**
     * nunca puede ser nulo, ya que es el dato necesario para identificar el origen de este producto
     */
    private String distribuidoraCodigo;

    /**
     * Retorna el valor del precio
     * @return en caso de que el valor sea null se retornara 0.0
     */
    public Double getPrecioPorCantidadEspecifica() {
        return precioPorCantidadEspecifica != null ? precioPorCantidadEspecifica : 0.0;
    }
}
