package distribuidora.scrapping.util.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import distribuidora.scrapping.dto.ProductoInternoDto;
import distribuidora.scrapping.entities.ProductoInterno;
import distribuidora.scrapping.repositories.postgres.ExternalProductRepository;
import distribuidora.scrapping.services.PresentationDtoConverter;
import distribuidora.scrapping.services.general.LookupService;

@Component
public class ProductoInternoConverter extends Converter<ProductoInterno, ProductoInternoDto> {
	@Autowired
	LookupService lookupService;

	@Autowired
	ExternalProductRepository productoRepository;

	@Autowired
	ExternalProductDtoConverter externalProductDtoConverter;

	@Autowired
	CategoryDtoConverter categoryDtoConverter;
	
	@Autowired
	PresentationDtoConverter presentationDtoConverter;

	@Override
	public ProductoInternoDto toDto(ProductoInterno entidad) {
		ProductoInternoDto dto = ProductoInternoDto.builder().id(entidad.getId()).nombre(entidad.getNombre())
				.descripcion(entidad.getDescripcion())
				.externalProduct(externalProductDtoConverter.toDto(entidad.getExternalProduct()))
				.fechaCreacion(entidad.getFechaCreacion()).fechaActualizacion(entidad.getFechaActualizacion())
				.precio(entidad.getPrecio()).porcentajeImpuesto(entidad.getPorcentajeImpuesto())
				.regulador(entidad.getRegulador()).build();

		if (entidad.getCategory() != null) {
			dto.setCategory(categoryDtoConverter.toDto(entidad.getCategory()));
		}

		if (entidad.getExternalProduct() != null) {
			dto.setExternalProduct(externalProductDtoConverter.toDto(entidad.getExternalProduct()));
		}
		dto.setPrecioTransporte(entidad.getPrecioTransporte());
		dto.setPrecioEmpaquetado(entidad.getPrecioEmpaquetado());
		dto.setPorcentajeGanancia(entidad.getPorcentajeGanancia());
		dto.setPresentation(presentationDtoConverter.toDto(entidad.getPresentation()));
		dto.setAvailable(entidad.getAvailable());
		return dto;
	}

	@Override
	public ProductoInterno toEntidad(ProductoInternoDto dto) {
		ProductoInterno entidad = ProductoInterno.builder().id(dto.getId()).nombre(dto.getNombre())
				.descripcion(dto.getDescripcion()).precio(dto.getPrecio() != null ? dto.getPrecio() : 0.0)
				.regulador(dto.getRegulador() != null ? dto.getRegulador() : 0.0)
				.externalProduct(externalProductDtoConverter.toEntidad(dto.getExternalProduct()))
				.category(categoryDtoConverter.toEntidad(dto.getCategory())).build();
		if (dto.getRegulador() != null)
			entidad.setRegulador(dto.getRegulador());

		if (dto.getExternalProduct() != null)
			entidad.setExternalProduct(externalProductDtoConverter.toEntidad(dto.getExternalProduct()));

		Double porcentajeImpuesto = dto.getPorcentajeImpuesto();
		entidad.setPorcentajeImpuesto(porcentajeImpuesto != null ? porcentajeImpuesto : 0.0);

		entidad.setPrecioTransporte(dto.getPrecioTransporte());
		entidad.setPrecioEmpaquetado(dto.getPrecioEmpaquetado());
		entidad.setPorcentajeGanancia(dto.getPorcentajeGanancia());
		entidad.setAvailable(dto.getAvailable());
		return entidad;
	}
}
