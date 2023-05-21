package distribuidora.scrapping.services.internal;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import distribuidora.scrapping.entities.ProductoEspecifico;
import distribuidora.scrapping.entities.ProductoInterno;
import distribuidora.scrapping.entities.productos.especificos.MelarEntidad;
import distribuidora.scrapping.entities.productos.especificos.VillaresEntidad;
import distribuidora.scrapping.enums.Distribuidora;

class InventorySystemImplTest {
	
	InventorySystemImpl service = new InventorySystemImpl();
	List<ProductoEspecifico> especificos;
	List<ProductoInterno> internos;

	@BeforeEach
	void setUp() throws Exception {
		especificos = new ArrayList<>();
		especificos.add(MelarEntidad.builder().distribuidora(Distribuidora.MELAR).externalId("1A").precioGranel(20.0).build());
		especificos.add(MelarEntidad.builder().distribuidora(Distribuidora.MELAR).externalId("3A").precioGranel(5.0).build());
		especificos.add(MelarEntidad.builder().distribuidora(Distribuidora.MELAR).externalId("8A").precioGranel(14.0).build());
		especificos.add(VillaresEntidad.builder().distribuidora(Distribuidora.VILLARES).externalId("11-22-336").precioLista(99.36).build());
		especificos.add(VillaresEntidad.builder().distribuidora(Distribuidora.VILLARES).externalId("11-2a2-336").precioLista(null).build());
		especificos.add(VillaresEntidad.builder().distribuidora(Distribuidora.VILLARES).externalId("11-11-336").precioLista(955.6).build());
		especificos.add(VillaresEntidad.builder().distribuidora(Distribuidora.VILLARES).externalId("11-1594-336").precioLista(748.23).build());
		especificos.add(VillaresEntidad.builder().distribuidora(Distribuidora.VILLARES).externalId("8A").precioLista(748.23).build());


		internos = new ArrayList<>();
		internos.add(ProductoInterno.builder().distribuidoraReferencia(Distribuidora.MELAR).codigoReferencia("1A").precio(30.2).build());
		internos.add(ProductoInterno.builder().distribuidoraReferencia(Distribuidora.VILLARES).codigoReferencia("11-22-336").precio(30.2).build());
		internos.add(ProductoInterno.builder().distribuidoraReferencia(Distribuidora.VILLARES).codigoReferencia("11-11-336").precio(30.2).build());
		internos.add(ProductoInterno.builder().distribuidoraReferencia(Distribuidora.MELAR).codigoReferencia("8A").precio(30.2).build());
		internos.add(ProductoInterno.builder().distribuidoraReferencia(Distribuidora.VILLARES).codigoReferencia("11-1594-336").precio(30.2).build());
		internos.add(ProductoInterno.builder().distribuidoraReferencia(Distribuidora.VILLARES).codigoReferencia("11-2a2-336").precio(30.2).build());
		internos.add(ProductoInterno.builder().distribuidoraReferencia(Distribuidora.VILLARES).codigoReferencia("8A").precio(30.2).build());
	}

	@Test
	void test() {
		service.actualizarPrecioConProductosEspecificos(especificos, internos);
		Map<Distribuidora, Map<String, Double>> mapInternos = internos.stream()
				.collect(Collectors.groupingBy(i -> i.getDistribuidoraReferencia(),
						Collectors.toMap(i -> i.getCodigoReferencia(), i -> i.getPrecio())));


		Map<Distribuidora, Map<String, Double>> mapEspecificos = especificos.stream()
				.collect(Collectors.groupingBy(e -> e.getDistribuidora(),
						Collectors.toMap(i -> i.getId(), i -> i.getPrecioExterno() != null ? i.getPrecioExterno() : 0.0)));

		assertEquals(mapInternos.get(Distribuidora.MELAR).get("8A"), mapEspecificos.get(Distribuidora.MELAR).get("8A"));
		assertEquals(mapInternos.get(Distribuidora.VILLARES).get("8A"), mapEspecificos.get(Distribuidora.VILLARES).get("8A"));
		assertNotEquals(mapInternos.get(Distribuidora.VILLARES).get("11-2a2-336"), mapEspecificos.get(Distribuidora.VILLARES).get("11-2a2-336"));

	}

}
