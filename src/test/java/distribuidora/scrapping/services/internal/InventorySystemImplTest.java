package distribuidora.scrapping.services.internal;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.LookupValor;
import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.entities.ProductoEspecifico;
import distribuidora.scrapping.entities.ProductoInterno;
import distribuidora.scrapping.entities.productos.especificos.MelarEntidad;
import distribuidora.scrapping.entities.productos.especificos.VillaresEntidad;
import distribuidora.scrapping.repositories.ProductoEspecificoRepository;
import distribuidora.scrapping.repositories.ProductoRepository;
import distribuidora.scrapping.repositories.postgres.ProductoInternoRepository;
import distribuidora.scrapping.services.general.LookupServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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

    List<Producto> especificos;
    List<ProductoInterno> internos;

    @BeforeEach
    void setUp()
            throws
            Exception {
		Date now = new Date();
        especificos = new ArrayList<>();
        especificos.addAll(Arrays.asList(
                Producto.builder().distribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_MELAR)
                        .id("1A").precioPorCantidadEspecifica(20.0).build(),
                Producto.builder().distribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_MELAR)
                        .id("3A").precioPorCantidadEspecifica(5.0).build(),
                Producto.builder().distribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_MELAR)
                        .id("8A").precioPorCantidadEspecifica(14.0).build(),
                Producto.builder().distribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_VILLARES)
                        .id("11-22-336").precioPorCantidadEspecifica(99.36).build(),
                Producto.builder().distribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_VILLARES)
                        .id("11-2a2-336").precioPorCantidadEspecifica(null).build(),
                Producto.builder().distribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_VILLARES)
                        .id("11-11-336").precioPorCantidadEspecifica(955.6).build(),
                Producto.builder().distribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_VILLARES)
                        .id("11-1594-336").precioPorCantidadEspecifica(748.23).build(),
                Producto.builder().distribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_VILLARES)
                        .id("8A").precioPorCantidadEspecifica(748.23).build()
        ));

        internos = new ArrayList<>();
        LookupValor melar = new LookupValor(Constantes.LV_DISTRIBUIDORA_MELAR);
        LookupValor villares = new LookupValor(Constantes.LV_DISTRIBUIDORA_VILLARES);

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

		Mockito.when(productoInternoRepository.findAllWhenHasPrecioReferencia())
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


        Map<String, Map<String, Double>> mapEspecificos = especificos.stream().collect(
                Collectors.groupingBy(Producto::getDistribuidoraCodigo,
                        Collectors.toMap(Producto::getId,Producto::getPrecioPorCantidadEspecifica)));

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


        Map<String, Map<String, Double>> mapEspecificos = especificos.stream().collect(
                Collectors.groupingBy(Producto::getDistribuidoraCodigo,
                        Collectors.toMap(Producto::getId,Producto::getPrecioPorCantidadEspecifica)));

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


        Map<String, Map<String, Double>> mapEspecificos = especificos.stream().collect(
                Collectors.groupingBy(Producto::getDistribuidoraCodigo,
                        Collectors.toMap(Producto::getId,Producto::getPrecioPorCantidadEspecifica)));

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
