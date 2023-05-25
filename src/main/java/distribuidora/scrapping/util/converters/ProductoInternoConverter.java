package distribuidora.scrapping.util.converters;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.LookupValor;
import distribuidora.scrapping.entities.ProductoInterno;
import distribuidora.scrapping.entities.dto.ProductoInternoDto;
import distribuidora.scrapping.services.general.LookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ProductoInternoConverter extends Converter<ProductoInterno, ProductoInternoDto>{
	@Autowired
	LookupService lookupService;

	@Override
	public ProductoInternoDto toDto(ProductoInterno productoInterno) {
		return ProductoInternoDto.builder()
				.id(productoInterno.getId())
				.nombre(productoInterno.getNombre())
				.descripcion(productoInterno.getDescripcion())
				.codigoReferencia(productoInterno.getCodigoReferencia())
				.fechaCreacion(productoInterno.getFechaCreacion())
				.fechaActualizacion(productoInterno.getFechaActualizacion())
				.distribuidoraReferenciaNombre(productoInterno.getDistribuidoraReferencia().getDescripcion())
				.distribuidoraReferenciaCodigo(productoInterno.getDistribuidoraReferencia().getCodigo())
				.build();
	}

	@Override
	public ProductoInterno toEntidad(ProductoInternoDto productoInternoDto) {
		Map<String, LookupValor> mapDistribuidoras = lookupService.getLookupValoresPorLookupTipoCodigo(
				Constantes.LV_DISTRIBUIDORAS).stream().collect(
				Collectors.toMap(LookupValor::getCodigo, Function.identity()));
		return ProductoInterno.builder()
				.id(productoInternoDto.getId())
				.fechaActualizacion(productoInternoDto.getFechaActualizacion())
				.fechaCreacion(productoInternoDto.getFechaCreacion())
				.nombre(productoInternoDto.getNombre())
				.descripcion(productoInternoDto.getDescripcion())
				.distribuidoraReferencia(mapDistribuidoras.get(productoInternoDto.getDistribuidoraReferenciaCodigo()))
				.build();
	}
}
