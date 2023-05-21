package distribuidora.scrapping.services.internal;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import distribuidora.scrapping.entities.ProductoEspecifico;
import distribuidora.scrapping.entities.ProductoInterno;
import distribuidora.scrapping.enums.Distribuidora;
import org.springframework.util.CollectionUtils;

public class InventorySystemImpl implements InventorySystem {

	@Override
	public int actualizarPreciosAutomatico() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void actualizarPrecioConProductosEspecificos(List<ProductoEspecifico> especificos,
			List<ProductoInterno> internos) {
		// TODO Auto-generated method stub
		// agrupo por distribuidora / codigo de referencia tanto interno como especifico
		Map<Distribuidora, Map<String, ProductoEspecifico>> mapEspecifico = especificos.stream()
				.collect(
						Collectors.groupingBy(e -> e.getDistribuidora(),
						Collectors.toMap(e -> e.getId(), Function.identity())));
		
		Map<Distribuidora, Map<String, ProductoInterno>> mapInterno = internos.stream()
				.collect(
						Collectors.groupingBy(e -> e.getDistribuidoraReferencia(),
						Collectors.toMap(e -> e.getCodigoReferencia(), Function.identity())));
		
		// recorro los internos por que son los unicos que me interesan
		for (Map.Entry<Distribuidora, Map<String, ProductoInterno>> mapInternoByDistribuidora : mapInterno.entrySet()) {
			Map<String, ProductoEspecifico> matchDistribuidora = mapEspecifico.getOrDefault(mapInternoByDistribuidora.getKey(), null);
			if (!CollectionUtils.isEmpty(matchDistribuidora)){
				for (Map.Entry<String, ProductoInterno> mapInternoCodigoReferenciaProducto : mapInternoByDistribuidora.getValue().entrySet()) {
					ProductoEspecifico matchProducto = matchDistribuidora.get(mapInternoCodigoReferenciaProducto.getKey());
					if (matchProducto != null){
						Double precio = matchProducto.getPrecioExterno();
						if (precio != null && precio > 0.0){
							mapInternoCodigoReferenciaProducto.getValue().setPrecio(precio);
						}
					}
				}
			}
		}
	}
}
