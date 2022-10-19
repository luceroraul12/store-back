package distribuidora.scrapping.entities;

import distribuidora.scrapping.enums.Distribuidora;
import lombok.Builder;
import lombok.Data;

/**
 * Es la entidad con la que llegan al FrontEnd
 */
@Data
@Builder
public class Producto {
    private String descripcion;
    private Double precioPorCantidadEspecifica;
    /**
     * nunca puede ser nulo, ya que es el dato necesario para identificar el origen de este producto
     * @see Distribuidora
     */
    private Distribuidora distribuidora;
}
