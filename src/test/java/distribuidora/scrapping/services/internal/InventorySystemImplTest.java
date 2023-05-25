package distribuidora.scrapping.services.internal;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.LookupValor;
import distribuidora.scrapping.entities.ProductoEspecifico;
import distribuidora.scrapping.entities.ProductoInterno;
import distribuidora.scrapping.entities.productos.especificos.MelarEntidad;
import distribuidora.scrapping.entities.productos.especificos.VillaresEntidad;
import distribuidora.scrapping.services.general.LookupServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(MockitoExtension.class)
class InventorySystemImplTest{

	@InjectMocks
	InventorySystemImpl service;

	@Mock
	LookupServiceImpl lookupService;

	List<ProductoEspecifico> especificos;
	List<ProductoInterno> internos;

	@BeforeEach
	void setUp()
			throws
			Exception {

		especificos = new ArrayList<>();
		especificos.add(MelarEntidad.builder().distribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_MELAR).externalId("1A")
		                            .precioGranel(20.0).build());
		especificos.add(MelarEntidad.builder().distribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_MELAR).externalId("3A")
		                            .precioGranel(5.0).build());
		especificos.add(MelarEntidad.builder().distribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_MELAR).externalId("8A")
		                            .precioGranel(14.0).build());
		especificos.add(VillaresEntidad.builder().distribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_VILLARES)
		                               .externalId("11-22-336").precioLista(99.36).build());
		especificos.add(VillaresEntidad.builder().distribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_VILLARES)
		                               .externalId("11-2a2-336").precioLista(null).build());
		especificos.add(VillaresEntidad.builder().distribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_VILLARES)
		                               .externalId("11-11-336").precioLista(955.6).build());
		especificos.add(VillaresEntidad.builder().distribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_VILLARES)
		                               .externalId("11-1594-336").precioLista(748.23).build());
		especificos.add(
				VillaresEntidad.builder().distribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_VILLARES).externalId("8A")
				               .precioLista(748.23).build());


		internos = new ArrayList<>();
		LookupValor melar = new LookupValor(Constantes.LV_DISTRIBUIDORA_MELAR);
		LookupValor villares = new LookupValor(Constantes.LV_DISTRIBUIDORA_VILLARES);

		internos.add(
				ProductoInterno.builder().distribuidoraReferencia(melar).codigoReferencia("1A").precio(30.2).build());
		internos.add(ProductoInterno.builder().distribuidoraReferencia(villares).codigoReferencia("11" + "-22" + "-336")
		                            .precio(30.2).build());
		internos.add(ProductoInterno.builder().distribuidoraReferencia(villares).codigoReferencia("11" + "-11" + "-336")
		                            .precio(30.2).build());
		internos.add(
				ProductoInterno.builder().distribuidoraReferencia(melar).codigoReferencia("8A").precio(30.2).build());
		internos.add(
				ProductoInterno.builder().distribuidoraReferencia(villares).codigoReferencia("11" + "-1594" + "-336")
				               .precio(30.2).build());
		internos.add(
				ProductoInterno.builder().distribuidoraReferencia(villares).codigoReferencia("11" + "-2a2" + "-336")
				               .precio(30.2).build());
		internos.add(ProductoInterno.builder().distribuidoraReferencia(villares).codigoReferencia("8A").precio(30.2)
		                            .build());

		Arrays.asList(new LookupValor(Constantes.LV_DISTRIBUIDORA_MELAR),
		              new LookupValor(Constantes.LV_DISTRIBUIDORA_VILLARES));



		Mockito.when(lookupService.getLookupValoresPorLookupTipoCodigo(Constantes.LV_DISTRIBUIDORAS))
				.thenReturn(Arrays.asList(new LookupValor(Constantes.LV_DISTRIBUIDORA_MELAR),
				                          new LookupValor(Constantes.LV_DISTRIBUIDORA_VILLARES)));
	}

	@Test
	void actualizacionPermitida(){
		service.actualizarPrecioConProductosEspecificos(especificos, internos);
		Map<String, Map<String, Double>> mapInternos = internos.stream().collect(
				Collectors.groupingBy(i -> i.getDistribuidoraReferencia().getCodigo(),
						Collectors.toMap(i -> i.getCodigoReferencia(), i -> i.getPrecio())));


		Map<String, Map<String, Double>> mapEspecificos = especificos.stream().collect(
				Collectors.groupingBy(e -> e.getDistribuidora(), Collectors.toMap(i -> i.getId(),
						i -> i.getPrecioExterno() != null ? i.getPrecioExterno() : 0.0)));

		assertEquals(mapInternos.get(Constantes.LV_DISTRIBUIDORA_MELAR).get("8A"),
				mapEspecificos.get(Constantes.LV_DISTRIBUIDORA_MELAR).get("8A"));
	}

	@Test
	void actualizacionCruzadaNoPermitida(){
		service.actualizarPrecioConProductosEspecificos(especificos, internos);
		Map<String, Map<String, Double>> mapInternos = internos.stream().collect(
				Collectors.groupingBy(i -> i.getDistribuidoraReferencia().getCodigo(),
						Collectors.toMap(i -> i.getCodigoReferencia(), i -> i.getPrecio())));


		Map<String, Map<String, Double>> mapEspecificos = especificos.stream().collect(
				Collectors.groupingBy(e -> e.getDistribuidora(), Collectors.toMap(i -> i.getId(),
						i -> i.getPrecioExterno() != null ? i.getPrecioExterno() : 0.0)));

		assertNotEquals(mapInternos.get(Constantes.LV_DISTRIBUIDORA_MELAR).get("8A"),
				mapEspecificos.get(Constantes.LV_DISTRIBUIDORA_VILLARES).get("8A"));
	}

	@Test
	void actualizacionNoPermitidaPorFaltaDeNuevoPrecio(){
		service.actualizarPrecioConProductosEspecificos(especificos, internos);
		Map<String, Map<String, Double>> mapInternos = internos.stream().collect(
				Collectors.groupingBy(i -> i.getDistribuidoraReferencia().getCodigo(),
						Collectors.toMap(i -> i.getCodigoReferencia(), i -> i.getPrecio())));


		Map<String, Map<String, Double>> mapEspecificos = especificos.stream().collect(
				Collectors.groupingBy(e -> e.getDistribuidora(), Collectors.toMap(i -> i.getId(),
						i -> i.getPrecioExterno() != null ? i.getPrecioExterno() : 0.0)));

		assertNotEquals(mapInternos.get(Constantes.LV_DISTRIBUIDORA_VILLARES).get("11-2a2-336"),
				mapEspecificos.get(Constantes.LV_DISTRIBUIDORA_VILLARES).get("11-2a2-336"));
	}

}
