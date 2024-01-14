package distribuidora.scrapping.util.converters;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.LookupValor;
import distribuidora.scrapping.entities.ProductoEspecifico;
import distribuidora.scrapping.entities.ProductoInterno;
import distribuidora.scrapping.services.general.LookupService;

public class ProductEspecificoInternoConverter
		extends
			Converter<ProductoEspecifico, ProductoInterno> {

	@Autowired
	LookupService lookupService;

	private Map<String, LookupValor> lvDistribuidoras = lookupService
			.getLookupValoresPorLookupTipoCodigo(Constantes.LV_DISTRIBUIDORAS)
			.stream().collect(Collectors.toMap(lv -> lv.getCodigo(),
					Function.identity()));

	@Override
	public ProductoInterno toDto(ProductoEspecifico productoEspecifico) {
		return ProductoInterno.builder()
				.codigoReferencia(productoEspecifico.getId())
				.precio(productoEspecifico.getPrecioExterno())
				.distribuidoraReferencia(lvDistribuidoras
						.get(productoEspecifico.getDistribuidora()))
				.build();
	}

	@Override
	public ProductoEspecifico toEntidad(ProductoInterno productoInternor) {
		return null;
	}
}
