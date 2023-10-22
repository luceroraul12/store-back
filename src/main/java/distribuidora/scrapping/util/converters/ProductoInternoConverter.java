package distribuidora.scrapping.util.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.dto.ProductoInternoDto;
import distribuidora.scrapping.entities.LookupValor;
import distribuidora.scrapping.entities.ProductoInterno;
import distribuidora.scrapping.repositories.postgres.ExternalProductRepository;
import distribuidora.scrapping.services.general.LookupService;

@Component
public class ProductoInternoConverter extends Converter<ProductoInterno, ProductoInternoDto>{
	@Autowired
	LookupService lookupService;

	@Autowired
	ExternalProductRepository productoRepository;
	
	@Autowired
	LookupValueDtoConverter lookupValueDtoConverter;
	
	@Autowired
	ExternalProductDtoConverter externalProductDtoConverter;

	@Override
	public ProductoInternoDto toDto(ProductoInterno entidad) {
		ProductoInternoDto dto = ProductoInternoDto.builder()
				.id(entidad.getId())
				.nombre(entidad.getNombre())
				.descripcion(entidad.getDescripcion())
				.externalProduct(externalProductDtoConverter.toDto(entidad.getExternalProduct()))
				.fechaCreacion(entidad.getFechaCreacion())
				.fechaActualizacion(entidad.getFechaActualizacion())
				.precio(entidad.getPrecio())
				.build();

		if (entidad.getLvCategoria() != null){
			dto.setCategory(lookupValueDtoConverter.toDto(entidad.getLvCategoria()));
		} else {
			LookupValor lv = new LookupValor();
			lv.setCodigo(Constantes.LV_CATEGORIAS_CEREALES);
			lv.setDescripcion(Constantes.LV_CATEGORIAS_CEREALES_DESCRIPTION);
			dto.setCategory(lookupValueDtoConverter.toDto(lv));
		}

		if (entidad.getExternalProduct() != null){
			dto.setExternalProduct(externalProductDtoConverter.toDto(entidad.getExternalProduct()));
		}
		dto.setPrecioTransporte(entidad.getPrecioTransporte());
		dto.setPrecioEmpaquetado(entidad.getPrecioEmpaquetado());
		dto.setPorcentajeGanancia(entidad.getPorcentajeGanancia());
		return dto;
	}

	@Override
	public ProductoInterno toEntidad(ProductoInternoDto dto) {
		ProductoInterno entidad = ProductoInterno.builder()
				.id(dto.getId())
				.nombre(dto.getNombre())
				.descripcion(dto.getDescripcion())
				.precio(dto.getPrecio() != null
						? dto.getPrecio()
						: 0.0)
				.externalProduct(externalProductDtoConverter.toEntidad(dto.getExternalProduct()))
				.category(lookupValueDtoConverter.toEntidad(dto.getCategory()))
				.build();
		
		if(dto.getExternalProduct() != null)
			entidad.setExternalProduct(externalProductDtoConverter.toEntidad(dto.getExternalProduct()));

		entidad.setPrecioTransporte(dto.getPrecioTransporte());
		entidad.setPrecioEmpaquetado(dto.getPrecioEmpaquetado());
		entidad.setPorcentajeGanancia(dto.getPorcentajeGanancia());
		return entidad;
	}
}
