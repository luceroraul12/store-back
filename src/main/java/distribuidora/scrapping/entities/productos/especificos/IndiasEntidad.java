package distribuidora.scrapping.entities.productos.especificos;

import distribuidora.scrapping.entities.ProductoEspecifico;
import distribuidora.scrapping.enums.Distribuidora;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
public class IndiasEntidad extends ProductoEspecifico {
    private String      rubro;
    private Integer     codigo;
    private String      descripcion;
    private Double      precio;

    @Builder

    public IndiasEntidad(Distribuidora distribuidora, String rubro, Integer codigo, String descripcion, Double precio) {
        super(distribuidora);
        this.rubro = rubro;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precio = precio;
    }
}
