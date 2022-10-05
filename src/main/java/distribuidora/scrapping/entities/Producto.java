package distribuidora.scrapping.entities;

import distribuidora.scrapping.enums.Distribuidora;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Producto {
    private String descripcion;
    private Double precioPorCantidadEspecifica;
    private Distribuidora distribuidora;
}
