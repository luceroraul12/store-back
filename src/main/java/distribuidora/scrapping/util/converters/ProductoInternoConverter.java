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
		ProductoInternoDto dto = ProductoInternoDto.builder()
				.id(productoInterno.getId())
				.nombre(productoInterno.getNombre())
				.descripcion(productoInterno.getDescripcion())
				.codigoReferencia(productoInterno.getCodigoReferencia())
				.fechaCreacion(productoInterno.getFechaCreacion())
				.fechaActualizacion(productoInterno.getFechaActualizacion())
				.precio(productoInterno.getPrecio())
				.build();

		if (productoInterno.getLvCategoria() != null){
			dto.setCategoriaCodigo(productoInterno.getLvCategoria().getCodigo());
			dto.setCategoriaNombre(productoInterno.getLvCategoria().getDescripcion());
		} else {
			dto.setCategoriaNombre("-");
			dto.setCategoriaCodigo("-");
		}

		if (productoInterno.getDistribuidoraReferencia() != null){
			LookupValor distribuidora = productoInterno.getDistribuidoraReferencia();
			dto.setDistribuidoraReferenciaCodigo(distribuidora.getCodigo());
			dto.setDistribuidoraReferenciaNombre(distribuidora.getDescripcion());
		}
		return dto;
	}

	@Override
	public ProductoInterno toEntidad(ProductoInternoDto productoInternoDto) {
		Map<String, LookupValor> mapDistribuidoras = lookupService.getLookupValoresPorLookupTipoCodigo(
				Constantes.LV_DISTRIBUIDORAS).stream()
				.collect(Collectors.toMap(LookupValor::getCodigo, Function.identity()));
		ProductoInterno entidad = ProductoInterno.builder()
				.id(productoInternoDto.getId())
				.nombre(productoInternoDto.getNombre())
				.descripcion(productoInternoDto.getDescripcion())
				.precio(productoInternoDto.getPrecio())
				.build();
		// si se le pasa el codigo de la distribuidora
		// deberias tener el codigo del producto al que queres hacer referencia
		if (productoInternoDto.getDistribuidoraReferenciaCodigo() != null){
			String distribuidoraCodigo = productoInternoDto.getDistribuidoraReferenciaCodigo();
			entidad.setDistribuidoraReferencia(mapDistribuidoras.get(distribuidoraCodigo));
			entidad.setCodigoReferencia(productoInternoDto.getCodigoReferencia());
		}
		if(productoInternoDto.getCategoriaCodigo() != null){
			LookupValor lvCategoria = lookupService.getlookupValorPorCodigo(productoInternoDto.getCategoriaCodigo());
			entidad.setLvCategoria(lvCategoria);
		}
		return entidad;
	}
}
