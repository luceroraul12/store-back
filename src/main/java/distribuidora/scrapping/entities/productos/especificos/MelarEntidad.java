package distribuidora.scrapping.entities.productos.especificos;

import distribuidora.scrapping.entities.ProductoEspecifico;
import distribuidora.scrapping.enums.Distribuidora;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MelarEntidad extends ProductoEspecifico {
    private String codigo;
    private String producto;
    private String fraccion;
    private String granel;
    private String origen;
    private String medida;
    //son precios que no tienen en cuenta el iva
    private Double precioFraccionado;
    private Double precioGranel;

    @Builder
    public MelarEntidad(Distribuidora distribuidora, String codigo, String producto, String fraccion, String granel, String origen, String medida, Double precioFraccionado, Double precioGranel) {
        super(distribuidora);
        this.codigo = codigo;
        this.producto = producto;
        this.fraccion = fraccion;
        this.granel = granel;
        this.origen = origen;
        this.medida = medida;
        this.precioFraccionado = precioFraccionado;
        this.precioGranel = precioGranel;
    }
}
