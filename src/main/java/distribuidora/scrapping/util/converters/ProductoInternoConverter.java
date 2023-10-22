package distribuidora.scrapping.util.converters;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.dto.ProductoInternoDto;
import distribuidora.scrapping.entities.LookupValor;
import distribuidora.scrapping.entities.ExternalProduct;
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
//			LookupValor distribuidora = entidad.getDistribuidoraReferencia();
//			dto.setDistribuidoraReferenciaCodigo(distribuidora.getCodigo());
//			dto.setDistribuidoraReferenciaNombre(distribuidora.getDescripcion());
		}
		dto.setPrecioTransporte(entidad.getPrecioTransporte());
		dto.setPrecioEmpaquetado(entidad.getPrecioEmpaquetado());
		dto.setPorcentajeGanancia(entidad.getPorcentajeGanancia());
		return dto;
	}

	@Override
	public ProductoInterno toEntidad(ProductoInternoDto dto) {
		Map<String, LookupValor> mapDistribuidoras = lookupService.getLookupValoresPorLookupTipoCodigo(
				Constantes.LV_DISTRIBUIDORAS).stream()
				.collect(Collectors.toMap(LookupValor::getCodigo, Function.identity()));
		ProductoInterno entidad = ProductoInterno.builder()
				.id(dto.getId())
				.nombre(dto.getNombre())
				.descripcion(dto.getDescripcion())
				.precio(dto.getPrecio() != null
						? dto.getPrecio()
						: 0.0)
				.build();
		
		if(dto.getExternalProduct() != null)
			entidad.setExternalProduct(externalProductDtoConverter.toEntidad(dto.getExternalProduct()));

		// si se le pasa el codigo de la distribuidora
		// deberias tener el codigo del producto al que queres hacer referencia
//		if (dto.getDistribuidoraReferenciaCodigo() != null){
//			String distribuidoraCodigo = dto.getDistribuidoraReferenciaCodigo();
//			entidad.setDistribuidoraReferencia(mapDistribuidoras.get(distribuidoraCodigo));
//			entidad.setCodigoReferencia(dto.getCodigoReferencia());
//		}
//		if(dto.getCategory() != null){
//			LookupValor lvCategoria = lookupService.getLookupValueByCode(dto.getCategory().getCode());
//			entidad.setLvCategoria(lvCategoria);
//		} else {
//			LookupValor lvCategoria = lookupService.getLookupValueByCode(Constantes.LV_CATEGORIAS_CEREALES);
//			entidad.setLvCategoria(lvCategoria);
//		}
		entidad.setPrecioTransporte(dto.getPrecioTransporte());
		entidad.setPrecioEmpaquetado(dto.getPrecioEmpaquetado());
		entidad.setPorcentajeGanancia(dto.getPorcentajeGanancia());
		return entidad;
	}

	@Override
	public List<ProductoInternoDto> toDtoList(List<ProductoInterno> productoInternos) {
		List<ProductoInternoDto> list = super.toDtoList(productoInternos);
//		Map<String, Map<Integer, ExternalProduct>> mapProductFixed = productoRepository.findAll().stream()
//				.collect(Collectors.groupingBy(ep -> ep.getDistribuidora().getCodigo(),
//						Collectors.toMap(p -> p.getId(), Function.identity())));
//		list = list.stream()
//				.map(p -> {
//					if (p.getDistribuidoraReferenciaCodigo() != null){
//						if (mapProductFixed.containsKey(p.getDistribuidoraReferenciaCodigo())){
//							Map<Integer, ExternalProduct> mapDistro = mapProductFixed.get(p.getDistribuidoraReferenciaCodigo());
//							if (mapDistro.containsKey(p.getCodigoReferencia())){
//								ExternalProduct productDistro = mapDistro.get(p.getCodigoReferencia());
//								if (productDistro != null){
//									p.setReferenciaNombre(productDistro.getTitle());
//								}
//							}
//						}
//					}
//					return p;
//				})
//				.collect(Collectors.toList());
		return list;
	}
}
