package distribuidora.scrapping.entities.productos.especificos;

import distribuidora.scrapping.entities.ProductoEspecifico;
import distribuidora.scrapping.enums.Distribuidora;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Getter
public class FacundoEntidad extends ProductoEspecifico {
    private String categoria;
    private String subcategoria;
    private String cantidad;
    private Double precioMayor;
    private Double precioMenor;
    @Builder
    public FacundoEntidad(Distribuidora distribuidora, String categoria, String subcategoria, String cantidad, Double precioMayor, Double precioMenor) {
        super(distribuidora);
        this.categoria = categoria;
        this.subcategoria = subcategoria;
        this.cantidad = cantidad;
        this.precioMayor = precioMayor;
        this.precioMenor = precioMenor;
    }
}
