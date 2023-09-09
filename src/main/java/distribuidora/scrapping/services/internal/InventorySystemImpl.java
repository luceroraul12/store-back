package distribuidora.scrapping.services.internal;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.CategoryHasUnit;
import distribuidora.scrapping.entities.LookupValor;
import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.entities.ProductoInterno;
import distribuidora.scrapping.dto.ProductoInternoDto;
import distribuidora.scrapping.repositories.ProductoRepository;
import distribuidora.scrapping.repositories.postgres.CategoryHasUnitRepository;
import distribuidora.scrapping.repositories.postgres.ProductoInternoRepository;
import distribuidora.scrapping.services.ProductoServicio;
import distribuidora.scrapping.services.general.LookupService;
import distribuidora.scrapping.util.converters.ProductoInternoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class InventorySystemImpl implements InventorySystem {

    @Autowired
    private LookupService lookupService;

    @Autowired
    private ProductoInternoRepository productoInternoRepository;

    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private CategoryHasUnitRepository categoryHasUnitRepository;

    @Autowired
    private ProductoInternoConverter productoInternoConverter;

    @Autowired
    private ProductoServicio productoServicio;

    @Override
    public int actualizarPreciosAutomatico() {
        // llamado a las bases de datos para obtener los productos especificos e internos
        List<ProductoInterno> productoInternos = productoInternoRepository.getProductosReferenciados();
        List<Producto> productoEspecificos = productoRepository.findAll();
        // tengo en cuenta la fecha al comenzar el proceso
        Date now = new Date();

        actualizarPrecioConProductosEspecificos(productoEspecificos, productoInternos);

        // solo tengo en cuenta los productos que tienen fecha de modificacion por delante que la fecha en la que se
        // inicia el actualizado
        Map<Boolean, List<ProductoInterno>> mapEsActualizadoProductosInternos = productoInternos.stream()
                .collect(Collectors.partitioningBy(p -> fechaComparator(p, now)));

        // actualizo los productos internos en la base de datos
        productoInternoRepository.saveAll(mapEsActualizadoProductosInternos.get(true));

        return mapEsActualizadoProductosInternos.get(true).size();
    }

    private boolean fechaComparator(ProductoInterno t, Date fechaComparable) {
        Date fecha = Objects.nonNull(t.getFechaActualizacion()) ? t.getFechaActualizacion() : t.getFechaCreacion();
        return fechaComparable.before(fecha);
    }

    @Override
    public void actualizarPrecioConProductosEspecificos(List<Producto> especificos,
                                                        List<ProductoInterno> internos) {
        Map<String, LookupValor> mapDistribuidoras = lookupService.getLookupValoresPorLookupTipoCodigo(
                Constantes.LV_DISTRIBUIDORAS).stream().collect(
                Collectors.toMap(d -> d.getCodigo(), Function.identity()));

        // agrupo por distribuidora / codigo de referencia tanto interno como especifico
        Map<String, Map<String, Producto>> mapEspecifico = especificos.stream().collect(
                Collectors.groupingBy(e -> e.getDistribuidoraCodigo(),
                        Collectors.toMap(e -> e.getId(), Function.identity())));

        //Agrupo por distribuidora de referencia
        Map<String, List<ProductoInterno>> mapDistribuidoraReferencia = internos.stream().collect(
                Collectors.groupingBy(e -> e.getDistribuidoraReferencia().getCodigo()));
        //Recorro la agrupacion por distribuidora de referencia
        for (Map.Entry<String, List<ProductoInterno>> entry : mapDistribuidoraReferencia.entrySet()) {
            String codigoDistribuidoraReferencia = entry.getKey();
            List<ProductoInterno> pDistribuidoraReferencia = entry.getValue();

            //Agrupo nuevamente los productos pero por codigo del producto de referencia
            Map<String, List<ProductoInterno>> mapCodigoReferencia = pDistribuidoraReferencia.stream()
                    .collect(Collectors.groupingBy(p -> p.getCodigoReferencia()));

            //Recorro cada uno de estos productos
            for (Map.Entry<String, List<ProductoInterno>> e : mapCodigoReferencia.entrySet()) {
                String codigoReferencia = e.getKey();
                List<ProductoInterno> productosCompartidos = e.getValue();
                Map<String, Producto> first = mapEspecifico.get(codigoDistribuidoraReferencia);
                if (first != null && first.containsKey(codigoReferencia)){
                    Producto matchProducto = first.get(codigoReferencia);
                    if (matchProducto != null) {
                        Double precio = matchProducto.getPrecioPorCantidadEspecifica();
                        for (ProductoInterno pi : productosCompartidos) {
                            if (precio != null && precio > 0.0) {
                                pi.setPrecio(precio);
                                pi.setFechaActualizacion(new Date());
                            }
                        }
                    }
                }
            }
        }

//        // recorro los internos por que son los unicos que me interesan
//        for (Map.Entry<String, Map<String, ProductoInterno>> mapInternoByDistribuidora : mapInterno.entrySet()) {
//            Map<String, Producto> matchDistribuidora = mapEspecifico.getOrDefault(
//                    mapInternoByDistribuidora.getKey(), null);
//            if (!CollectionUtils.isEmpty(matchDistribuidora)) {
//                for (Map.Entry<String, ProductoInterno> mapInternoCodigoReferenciaProducto : mapInternoByDistribuidora.getValue()
//                        .entrySet()) {
//                    Producto matchProducto = matchDistribuidora.get(
//                            mapInternoCodigoReferenciaProducto.getKey());
//                    if (matchProducto != null) {
//                        Double precio = matchProducto.getPrecioPorCantidadEspecifica();
//                        if (precio != null && precio > 0.0) {
//                            mapInternoCodigoReferenciaProducto.getValue().setPrecio(precio);
//                            mapInternoCodigoReferenciaProducto.getValue().setFechaActualizacion(new Date());
//                        }
//                    }
//                }
//            }
//        }
    }
    @Override
    public ProductoInternoDto crearProducto(ProductoInternoDto dto) {
        if (dto.getId() != null)
            return null;

        ProductoInterno producto = productoInternoConverter.toEntidad(dto);
        producto.setFechaCreacion(new Date());
        ProductoInterno productoGuardado = productoInternoRepository.save(producto);
        return productoInternoConverter.toDto(productoGuardado);
    }

    //TODO: Ordenar este metodo, que si bien funciona parece que se esta empezando a complicar la lectura
    @Override
    public ProductoInternoDto modificarProducto(ProductoInternoDto dto) {
        if (dto.getId() == null)
            return null;

        ProductoInterno oldEntidadInterno = productoInternoRepository.getReferenceById(dto.getId());
        if (oldEntidadInterno == null)
            return null;

        ProductoInterno newEntidadInterno = productoInternoConverter.toEntidad(dto);
        Producto productoVinculado = null;

        //Actualizo el precio del oldEntidadInterno con el precio del oldEntidadInterno si es que existe
        if(newEntidadInterno.getDistribuidoraReferencia() != null
                && oldEntidadInterno.getDistribuidoraReferencia() != null) {
            if (!oldEntidadInterno.getDistribuidoraReferencia().getCodigo()
                    .equalsIgnoreCase(newEntidadInterno.getDistribuidoraReferencia().getCodigo())
                    && !oldEntidadInterno.getCodigoReferencia()
                    .equalsIgnoreCase(newEntidadInterno.getCodigoReferencia())){
                String distribuidoraCodigo = newEntidadInterno.getDistribuidoraReferencia().getCodigo();
                String idReferencia = newEntidadInterno.getCodigoReferencia();
                productoVinculado = productoServicio.getProductoByDistribuidoraCodigoAndId(distribuidoraCodigo,idReferencia);
                newEntidadInterno.setCodigoReferencia(productoVinculado.getId());
            }
        }

        newEntidadInterno.setFechaCreacion(oldEntidadInterno.getFechaCreacion());
        verificaryActualizarFechaModificacio(oldEntidadInterno, newEntidadInterno);


        ProductoInterno productoGuardado = productoInternoRepository.save(newEntidadInterno);

        dto = productoInternoConverter.toDto(productoGuardado);
        if (productoVinculado != null)
            dto.setReferenciaNombre(productoVinculado.getDescripcion());

        return dto;
    }

    @Override
    public List<ProductoInternoDto> eliminarProductos(List<Integer> productoInternoIds) {
        List<ProductoInterno> productosEncontrados = productoInternoRepository.getProductosPorIds(productoInternoIds);
        List<Integer> productoIdsEncontrados = productosEncontrados.stream()
                        .map(ProductoInterno::getId)
                                .collect(Collectors.toList());
        productoInternoRepository.deleteAllById(productoIdsEncontrados);
        return productoInternoConverter.toDtoList(productosEncontrados);
    }

    @Override
    public List<ProductoInternoDto> getProductos() {
        List<ProductoInterno> productos = productoInternoRepository.getAllProductos();
        return productoInternoConverter.toDtoList(productos);
    }

    @Override
    public List<ProductoInternoDto> updateManyProducto(List<ProductoInternoDto> dtos) {
        // Busco todos los productos por Id con un unico llamado
        List<Integer> productoInternoIds = dtos.stream()
                        .map(ProductoInternoDto::getId)
                                .collect(Collectors.toList());
        List<ProductoInternoDto> resultado = new ArrayList<>();
        for (ProductoInternoDto dto : dtos) {
            resultado.add(modificarProducto(dto));
        }
        // retorno todos
        return resultado;
    }

    /**
     * Encargado de verificar si hay cambios en alguno tipo de producto como:
     * precio base
     * precio de transporte
     * precio de empaquetado
     * porcentaje de ganancia
     *
     * Para poder decidir cuando debe actualizar la fecha de actualizacion
     * @param oldEntidadInterno
     * @param newEntidadInterno
     */
    private void verificaryActualizarFechaModificacio(ProductoInterno oldEntidadInterno, ProductoInterno newEntidadInterno){
        boolean priceUpdated = false;
        boolean priceTransportUpdated = false;
        boolean pricePackageUpdated = false;
        boolean priceGainUpdated = false;
        if (newEntidadInterno.getPrecio() != null) {
            priceUpdated = !newEntidadInterno.getPrecio().equals(oldEntidadInterno.getPrecio());
        }
        if (newEntidadInterno.getPrecioTransporte() != null) {
            priceTransportUpdated = !newEntidadInterno.getPrecioTransporte().equals(oldEntidadInterno.getPrecioTransporte());
        }
        if (newEntidadInterno.getPrecioEmpaquetado() != null) {
            pricePackageUpdated = !newEntidadInterno.getPrecioEmpaquetado().equals(oldEntidadInterno.getPrecioEmpaquetado());
        }
        if (newEntidadInterno.getPorcentajeGanancia() != null)
            priceGainUpdated = !newEntidadInterno.getPorcentajeGanancia().equals(oldEntidadInterno.getPorcentajeGanancia());

        if (priceUpdated || priceTransportUpdated || pricePackageUpdated || priceGainUpdated){
            newEntidadInterno.setFechaActualizacion(new Date());
        } else {
            newEntidadInterno.setFechaActualizacion(oldEntidadInterno.getFechaActualizacion());
        }
    }

	@Override
	public List<CategoryHasUnit> getCategoryDtoList() {
		return categoryHasUnitRepository.findAll();
	};
}
