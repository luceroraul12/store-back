package distribuidora.scrapping.entities.productos.especificos;


import distribuidora.scrapping.entities.ProductoEspecifico;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class SudamerikEntidad extends ProductoEspecifico {
    private String nombreProducto;
    private String cantidadEspecifca;
    private Double precio;
    private List<SudamerikConjuntoEspecifico> cantidadesEspecificas;


    @Data
    @Builder
    public static class SudamerikConjuntoEspecifico {
        private String cantidadEspecifica;
        private Double precio;
    }

}
