package distribuidora.scrapping.entities.productos.especificos;

import distribuidora.scrapping.entities.ProductoEspecifico;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
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



}
