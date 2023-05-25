package distribuidora.scrapping.services.internal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.LookupValor;
import distribuidora.scrapping.entities.ProductoEspecifico;
import distribuidora.scrapping.entities.ProductoInterno;
import distribuidora.scrapping.entities.dto.ProductoInternoDto;
import distribuidora.scrapping.services.general.LookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

public class InventorySystemImpl
        implements InventorySystem {

    @Autowired
    private LookupService lookupService;

    @Override
    public int actualizarPreciosAutomatico() {
        // llamado a las bases de datos para obtener los productos especificos e internos
        List<ProductoInterno> productoInternos = new ArrayList<>();
        List<ProductoEspecifico> productoEspecificos = new ArrayList<>();
        // tengo en cuenta la fecha al comenzar el proceso
        Date now = new Date();

        actualizarPrecioConProductosEspecificos(productoEspecificos, productoInternos);

        // solo tengo en cuenta los productos que tienen fecha de modificacion por delante que la fecha en la que se
        // inicia el actualizado
        Map<Boolean, List<ProductoInterno>> mapEsActualizadoProductosInternos = productoInternos.stream()
                .collect(Collectors.partitioningBy(producto -> now.before(producto.getFechaActualizacion())));

        return mapEsActualizadoProductosInternos.get(true).size();
    }

    @Override
    public void actualizarPrecioConProductosEspecificos(List<ProductoEspecifico> especificos,
                                                        List<ProductoInterno> internos) {
        Map<String, LookupValor> mapDistribuidoras = lookupService.getLookupValoresPorLookupTipoCodigo(
                Constantes.LV_DISTRIBUIDORAS).stream().collect(
                Collectors.toMap(d -> d.getCodigo(), Function.identity()));

        // agrupo por distribuidora / codigo de referencia tanto interno como especifico
        Map<String, Map<String, ProductoEspecifico>> mapEspecifico = especificos.stream().collect(
                Collectors.groupingBy(e -> e.getDistribuidora(),
                        Collectors.toMap(e -> e.getId(), Function.identity())));

        Map<String, Map<String, ProductoInterno>> mapInterno = internos.stream().collect(
                Collectors.groupingBy(e -> e.getDistribuidoraReferencia().getCodigo(),
                        Collectors.toMap(e -> e.getCodigoReferencia(), Function.identity())));

        // recorro los internos por que son los unicos que me interesan
        for (Map.Entry<String, Map<String, ProductoInterno>> mapInternoByDistribuidora : mapInterno.entrySet()) {
            Map<String, ProductoEspecifico> matchDistribuidora = mapEspecifico.getOrDefault(
                    mapInternoByDistribuidora.getKey(), null);
            if (!CollectionUtils.isEmpty(matchDistribuidora)) {
                for (Map.Entry<String, ProductoInterno> mapInternoCodigoReferenciaProducto : mapInternoByDistribuidora.getValue()
                        .entrySet()) {
                    ProductoEspecifico matchProducto = matchDistribuidora.get(
                            mapInternoCodigoReferenciaProducto.getKey());
                    if (matchProducto != null) {
                        Double precio = matchProducto.getPrecioExterno();
                        if (precio != null && precio > 0.0) {
                            mapInternoCodigoReferenciaProducto.getValue().setPrecio(precio);
                        }
                    }
                }
            }
        }
    }

    @Override
    public Integer crearProductos(List<ProductoInternoDto> dtos) {
        return null;
    }
}
