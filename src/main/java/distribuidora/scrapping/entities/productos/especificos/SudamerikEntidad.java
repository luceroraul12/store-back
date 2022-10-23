package distribuidora.scrapping.entities.productos.especificos;


import distribuidora.scrapping.entities.ProductoEspecifico;
import distribuidora.scrapping.enums.Distribuidora;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
public class SudamerikEntidad extends ProductoEspecifico {
    private String nombreProducto;
    private List<SudamerikConjuntoEspecifico> cantidadesEspecificas;

    @Builder
    public SudamerikEntidad(Distribuidora distribuidora, String nombreProducto, List<SudamerikConjuntoEspecifico> cantidadesEspecificas) {
        super(distribuidora);
        this.nombreProducto = nombreProducto;
        this.cantidadesEspecificas = cantidadesEspecificas;
    }

    @Data
    @Builder
    public static class SudamerikConjuntoEspecifico {
        private String cantidadEspecifica;
        private Double precio;
    }

}
