package distribuidora.scrapping.util.converters;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.LookupValor;
import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.entities.ProductoInterno;
import distribuidora.scrapping.entities.dto.ProductoInternoDto;
import distribuidora.scrapping.repositories.ProductoRepository;
import distribuidora.scrapping.services.general.LookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ProductoInternoConverter extends Converter<ProductoInterno, ProductoInternoDto>{
	@Autowired
	LookupService lookupService;

	@Autowired
	ProductoRepository productoRepository;

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
			dto.setLvCategoria(productoInterno.getLvCategoria());
		} else {
			LookupValor lv = new LookupValor();
			lv.setCodigo("CEREALES");
			lv.setDescripcion("CEREALES");
			dto.setLvCategoria(lv);
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
				.precio(productoInternoDto.getPrecio() != null ? productoInternoDto.getPrecio() : 0.0)
				.build();

		// si se le pasa el codigo de la distribuidora
		// deberias tener el codigo del producto al que queres hacer referencia
		if (productoInternoDto.getDistribuidoraReferenciaCodigo() != null){
			String distribuidoraCodigo = productoInternoDto.getDistribuidoraReferenciaCodigo();
			entidad.setDistribuidoraReferencia(mapDistribuidoras.get(distribuidoraCodigo));
			entidad.setCodigoReferencia(productoInternoDto.getCodigoReferencia());
		}
		if(productoInternoDto.getLvCategoria() != null){
			LookupValor lvCategoria = lookupService.getlookupValorPorCodigo(productoInternoDto.getLvCategoria().getCodigo());
			entidad.setLvCategoria(lvCategoria);
		}
		return entidad;
	}

	@Override
	public List<ProductoInternoDto> toDtoList(List<ProductoInterno> productoInternos) {
		List<ProductoInternoDto> list = super.toDtoList(productoInternos);
		Map<String, Map<String, Producto>> mapProductFixed = productoRepository.findAll().stream()
				.collect(Collectors.groupingBy(Producto::getDistribuidoraCodigo,
						Collectors.toMap(p -> p.getId(), Function.identity())));
		list = list.stream()
				.map(p -> {
					// TODO: reveer esto por que algunas veces viene null
					if (p.getDistribuidoraReferenciaCodigo() != null){
						if (mapProductFixed.containsKey(p.getDistribuidoraReferenciaCodigo())){
							Map<String, Producto> mapDistro = mapProductFixed.get(p.getDistribuidoraReferenciaCodigo());
							if (mapDistro.containsKey(p.getCodigoReferencia())){
								Producto productDistro = mapDistro.get(p.getCodigoReferencia());
								if (productDistro != null){
									p.setReferenciaNombre(productDistro.getDescripcion());
								}
							}
						}
					}
					return p;
				})
				.collect(Collectors.toList());
		return list;
	}
}
