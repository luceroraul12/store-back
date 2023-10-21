package distribuidora.scrapping.services.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.ExternalProduct;
import distribuidora.scrapping.entities.LookupValor;
import distribuidora.scrapping.entities.ProductoInterno;
import distribuidora.scrapping.repositories.postgres.ProductoInternoRepository;
import distribuidora.scrapping.repositories.postgres.ProductoRepository;
import distribuidora.scrapping.services.general.LookupServiceImpl;

@ExtendWith(MockitoExtension.class)
class InventorySystemImplTest {

    @InjectMocks
    InventorySystemImpl service;

    @Mock
    LookupServiceImpl lookupService;

    @Mock
	ProductoInternoRepository productoInternoRepository;

    @Mock
    ProductoRepository productoRepository;

    List<ExternalProduct> especificos;
    List<ProductoInterno> internos;

    @BeforeEach
    void setUp()
            throws
            Exception {
		Date now = new Date();
        especificos = new ArrayList<>();
        LookupValor melar = new LookupValor(Constantes.LV_DISTRIBUIDORA_MELAR);
        LookupValor villares = new LookupValor(Constantes.LV_DISTRIBUIDORA_VILLARES);
        especificos.addAll(Arrays.asList(
                ExternalProduct.builder().distribuidora(melar)
                        .code("1A").price(20.0).build(),
                ExternalProduct.builder().distribuidora(melar)
                        .code("3A").price(5.0).build(),
                ExternalProduct.builder().distribuidora(melar)
                        .code("8A").price(14.0).build(),
                ExternalProduct.builder().distribuidora(villares)
                        .code("11-22-336").price(99.36).build(),
                ExternalProduct.builder().distribuidora(villares)
                        .code("11-2a2-336").price(null).build(),
                ExternalProduct.builder().distribuidora(villares)
                        .code("11-11-336").price(955.6).build(),
                ExternalProduct.builder().distribuidora(villares)
                        .code("11-1594-336").price(748.23).build(),
                ExternalProduct.builder().distribuidora(villares)
                        .code("8A").price(748.23).build()
        ));

        internos = new ArrayList<>();

        internos.addAll(Arrays.asList(
                ProductoInterno.builder().distribuidoraReferencia(melar).codigoReferencia("1A")
                        .precio(30.2).build(),
                ProductoInterno.builder().distribuidoraReferencia(villares).codigoReferencia("11-22-336")
                        .precio(30.2).build(),
                ProductoInterno.builder().distribuidoraReferencia(villares).codigoReferencia("11-11-336")
                        .precio(30.2).build(),
                ProductoInterno.builder().distribuidoraReferencia(melar).codigoReferencia("8A")
                        .precio(30.2).build(),
                ProductoInterno.builder().distribuidoraReferencia(villares).codigoReferencia("11-1594-336")
                        .precio(30.2).fechaActualizacion(now).build(),
                ProductoInterno.builder().distribuidoraReferencia(villares).codigoReferencia("11-2a2-336")
                        .precio(30.2).fechaActualizacion(now).build(),
                ProductoInterno.builder().distribuidoraReferencia(villares).codigoReferencia("8A")
                        .precio(30.2).build()
        ));


        Mockito.when(lookupService.getLookupValoresPorLookupTipoCodigo(Constantes.LV_DISTRIBUIDORAS))
                .thenReturn(Arrays.asList(new LookupValor(Constantes.LV_DISTRIBUIDORA_MELAR),
                        new LookupValor(Constantes.LV_DISTRIBUIDORA_VILLARES)));

		Mockito.when(productoInternoRepository.getProductosReferenciados())
				.thenReturn(internos);

		Mockito.when(productoRepository.findAll())
				.thenReturn(especificos);
    }

    @Test
    void actualizacionPermitida() {
        service.actualizarPreciosAutomatico();
        Map<String, Map<String, Double>> mapInternos = internos.stream().collect(
                Collectors.groupingBy(i -> i.getDistribuidoraReferencia().getCodigo(),
                        Collectors.toMap(ProductoInterno::getCodigoReferencia, ProductoInterno::getPrecio)));


        Map<String, Map<Integer, Double>> mapEspecificos = especificos.stream().collect(
                Collectors.groupingBy(ep -> ep.getDistribuidora().getCodigo(),
                        Collectors.toMap(ExternalProduct::getId,ExternalProduct::getPrecioPorCantidadEspecifica)));

        assertEquals(
                mapInternos.get(Constantes.LV_DISTRIBUIDORA_MELAR).get("8A"),
                mapEspecificos.get(Constantes.LV_DISTRIBUIDORA_MELAR).get("8A"));
    }

    @Test
    void actualizacionCruzadaNoPermitida() {
		service.actualizarPreciosAutomatico();
        Map<String, Map<String, Double>> mapInternos = internos.stream().collect(
                Collectors.groupingBy(i -> i.getDistribuidoraReferencia().getCodigo(),
                        Collectors.toMap(ProductoInterno::getCodigoReferencia, ProductoInterno::getPrecio)));


        Map<String, Map<Integer, Double>> mapEspecificos = especificos.stream().collect(
                Collectors.groupingBy(ep -> ep.getDistribuidora().getCodigo(),
                        Collectors.toMap(ExternalProduct::getId,ExternalProduct::getPrecioPorCantidadEspecifica)));

        assertNotEquals(
                mapInternos.get(Constantes.LV_DISTRIBUIDORA_MELAR).get("8A"),
                mapEspecificos.get(Constantes.LV_DISTRIBUIDORA_VILLARES).get("8A"));
    }

    @Test
    void actualizacionNoPermitidaPorFaltaDeNuevoPrecio() {
		service.actualizarPreciosAutomatico();
        Map<String, Map<String, Double>> mapInternos = internos.stream().collect(
                Collectors.groupingBy(i -> i.getDistribuidoraReferencia().getCodigo(),
                        Collectors.toMap(ProductoInterno::getCodigoReferencia, ProductoInterno::getPrecio)));


        Map<String, Map<Integer, Double>> mapEspecificos = especificos.stream().collect(
                Collectors.groupingBy(ep -> ep.getDistribuidora().getCodigo(),
                        Collectors.toMap(ExternalProduct::getId,ExternalProduct::getPrecioPorCantidadEspecifica)));

        assertNotEquals(
                mapInternos.get(Constantes.LV_DISTRIBUIDORA_VILLARES).get("11-2a2-336"),
                mapEspecificos.get(Constantes.LV_DISTRIBUIDORA_VILLARES).get("11-2a2-336"));
    }

    @Test
    void cantidadProductosActualizados() {
        int actualizados = service.actualizarPreciosAutomatico();
        assertEquals(6, actualizados);
    }

}
