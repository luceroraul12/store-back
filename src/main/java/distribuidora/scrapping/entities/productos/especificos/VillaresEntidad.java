package distribuidora.scrapping.entities.productos.especificos;

import distribuidora.scrapping.entities.ProductoEspecifico;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VillaresEntidad extends ProductoEspecifico {
    private String descripcion;
    private String cantidad;
    private String cantidadMinima;
    private String detalle;
    private String marca;
    private String unidad;

    private Double precioLista;
    private Double precioPrimerBeneficio;
    private Double precioSegundoBeneficio;
    private Double precioTercerBeneficio;

    @Builder
   	public VillaresEntidad(String externalId, String distribuidoraCodigo, String descripcion, String cantidad,
			String cantidadMinima, String detalle, String marca, String unidad, Double precioLista,
			Double precioPrimerBeneficio, Double precioSegundoBeneficio, Double precioTercerBeneficio) {
		super(externalId, distribuidoraCodigo);
		this.descripcion = descripcion;
		this.cantidad = cantidad;
		this.cantidadMinima = cantidadMinima;
		this.detalle = detalle;
		this.marca = marca;
		this.unidad = unidad;
		this.precioLista = precioLista;
		this.precioPrimerBeneficio = precioPrimerBeneficio;
		this.precioSegundoBeneficio = precioSegundoBeneficio;
		this.precioTercerBeneficio = precioTercerBeneficio;
	}

	@Override
	public Double getPrecioExterno() {
		// TODO Auto-generated method stub
		return precioLista;
	}
    
    
    
}
