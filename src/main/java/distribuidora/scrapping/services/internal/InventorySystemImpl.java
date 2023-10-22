package distribuidora.scrapping.services.internal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.dto.CategoryHasUnitDto;
import distribuidora.scrapping.dto.ProductoInternoDto;
import distribuidora.scrapping.entities.CategoryHasUnit;
import distribuidora.scrapping.entities.DatosDistribuidora;
import distribuidora.scrapping.entities.ExternalProduct;
import distribuidora.scrapping.entities.ProductoInterno;
import distribuidora.scrapping.repositories.DatosDistribuidoraRepository;
import distribuidora.scrapping.repositories.postgres.CategoryHasUnitRepository;
import distribuidora.scrapping.repositories.postgres.ExternalProductRepository;
import distribuidora.scrapping.repositories.postgres.ProductoInternoRepository;
import distribuidora.scrapping.services.ExternalProductService;
import distribuidora.scrapping.services.general.LookupService;
import distribuidora.scrapping.util.converters.CategoryHasUnitDtoConverter;
import distribuidora.scrapping.util.converters.ProductoInternoConverter;

@Service
public class InventorySystemImpl implements InventorySystem {

	@Autowired
	private ProductoInternoRepository productoInternoRepository;

	@Autowired
	private ExternalProductRepository productoRepository;

	@Autowired
	private CategoryHasUnitRepository categoryHasUnitRepository;

	@Autowired
	private ProductoInternoConverter productoInternoConverter;

	@Autowired
	private ExternalProductService productoServicio;

	@Autowired
	private CategoryHasUnitDtoConverter categoryHasUnitDtoConverter;
	
	@Autowired
	private LookupService lookupService;
	
	@Autowired
	private DatosDistribuidoraRepository datosDistribuidoraRepository;

	@Override
	public int actualizarPreciosAutomatico() {
		// llamado a las bases de datos para obtener los productos especificos e
		// internos
		List<ProductoInterno> productoInternos = productoInternoRepository
				.getProductosReferenciados();
		List<ExternalProduct> productoEspecificos = productoRepository.findAll();
		// tengo en cuenta la fecha al comenzar el proceso
		Date now = new Date();

		actualizarPrecioConProductosEspecificos(productoEspecificos,
				productoInternos);

		// solo tengo en cuenta los productos que tienen fecha de modificacion
		// por delante que la fecha en la que se
		// inicia el actualizado
		Map<Boolean, List<ProductoInterno>> mapEsActualizadoProductosInternos = productoInternos
				.stream().collect(Collectors
						.partitioningBy(p -> fechaComparator(p, now)));

		// actualizo los productos internos en la base de datos
		productoInternoRepository
				.saveAll(mapEsActualizadoProductosInternos.get(true));

		return mapEsActualizadoProductosInternos.get(true).size();
	}

	private boolean fechaComparator(ProductoInterno t, Date fechaComparable) {
		Date fecha = Objects.nonNull(t.getFechaActualizacion())
				? t.getFechaActualizacion()
				: t.getFechaCreacion();
		return fechaComparable.before(fecha);
	}

	@Override
	public void actualizarPrecioConProductosEspecificos(
			List<ExternalProduct> especificos, List<ProductoInterno> internos) {
		// agrupo por distribuidora / codigo de referencia tanto interno como
		// especifico
		Map<String, Map<Integer, ExternalProduct>> mapEspecifico = especificos.stream()
				.collect(Collectors.groupingBy(e -> e.getDistribuidora().getCodigo(),
						Collectors.toMap(e -> e.getId(), Function.identity())));

		// Agrupo por distribuidora de referencia
		Map<String, List<ProductoInterno>> mapDistribuidoraReferencia = internos
				.stream().collect(Collectors.groupingBy(
						e -> e.getDistribuidoraReferencia().getCodigo()));
		// Recorro la agrupacion por distribuidora de referencia
		for (Map.Entry<String, List<ProductoInterno>> entry : mapDistribuidoraReferencia
				.entrySet()) {
			String codigoDistribuidoraReferencia = entry.getKey();
			List<ProductoInterno> pDistribuidoraReferencia = entry.getValue();

			// Agrupo nuevamente los productos pero por codigo del producto de
			// referencia
			Map<String, List<ProductoInterno>> mapCodigoReferencia = pDistribuidoraReferencia
					.stream().collect(Collectors
							.groupingBy(p -> p.getCodigoReferencia()));

			// Recorro cada uno de estos productos
			for (Map.Entry<String, List<ProductoInterno>> e : mapCodigoReferencia
					.entrySet()) {
				String codigoReferencia = e.getKey();
				List<ProductoInterno> productosCompartidos = e.getValue();
				Map<Integer, ExternalProduct> first = mapEspecifico
						.get(codigoDistribuidoraReferencia);
				if (first != null && first.containsKey(codigoReferencia)) {
					ExternalProduct matchProducto = first.get(codigoReferencia);
					if (matchProducto != null) {
						Double precio = matchProducto
								.getPrecioPorCantidadEspecifica();
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

	}
	@Override
	public ProductoInternoDto crearProducto(ProductoInternoDto dto) {
		if (dto.getId() != null)
			return null;

		ProductoInterno producto = productoInternoConverter.toEntidad(dto);
		producto.setFechaCreacion(new Date());
		ProductoInterno productoGuardado = productoInternoRepository
				.save(producto);
		return productoInternoConverter.toDto(productoGuardado);
	}

	// TODO: Ordenar este metodo, que si bien funciona parece que se esta
	// empezando a complicar la lectura
	@Override
	public ProductoInternoDto modificarProducto(ProductoInternoDto dto) {
		if (dto.getId() == null)
			return null;

		ProductoInterno oldEntidadInterno = productoInternoRepository
				.getReferenceById(dto.getId());
		if (oldEntidadInterno == null)
			return null;

		ProductoInterno newEntidadInterno = productoInternoConverter
				.toEntidad(dto);

		newEntidadInterno
				.setFechaCreacion(oldEntidadInterno.getFechaCreacion());
		verifyAndUpdateDateModified(oldEntidadInterno,
				newEntidadInterno);

		ProductoInterno productoGuardado = productoInternoRepository
				.save(newEntidadInterno);

		dto = productoInternoConverter.toDto(productoGuardado);

		return dto;
	}

	@Override
	public List<ProductoInternoDto> eliminarProductos(
			List<Integer> productoInternoIds) {
		List<ProductoInterno> productosEncontrados = productoInternoRepository
				.getProductosPorIds(productoInternoIds);
		List<Integer> productoIdsEncontrados = productosEncontrados.stream()
				.map(ProductoInterno::getId).collect(Collectors.toList());
		productoInternoRepository.deleteAllById(productoIdsEncontrados);
		return productoInternoConverter.toDtoList(productosEncontrados);
	}

	@Override
	public List<ProductoInternoDto> getProductos() {
		List<ProductoInterno> productos = productoInternoRepository
				.getAllProductos();
		return productoInternoConverter.toDtoList(productos);
	}

	@Override
	public List<ProductoInternoDto> updateManyProducto(
			List<ProductoInternoDto> dtos) {
		List<ProductoInternoDto> resultado = new ArrayList<>();
		for (ProductoInternoDto dto : dtos) {
			resultado.add(modificarProducto(dto));
		}
		// retorno todos
		return resultado;
	}

	/**
	 * Encargado de verificar si hay cambios en alguno tipo de producto como:
	 * precio base precio de transporte precio de empaquetado porcentaje de
	 * ganancia
	 *
	 * Para poder decidir cuando debe actualizar la fecha de actualizacion
	 * 
	 * @param oldEntidadInterno
	 * @param newEntidadInterno
	 */
	private void verifyAndUpdateDateModified(
			ProductoInterno oldEntidadInterno,
			ProductoInterno newEntidadInterno) {
		boolean priceUpdated = false;
		boolean priceTransportUpdated = false;
		boolean pricePackageUpdated = false;
		boolean priceGainUpdated = false;
		if (newEntidadInterno.getPrecio() != null) {
			priceUpdated = !newEntidadInterno.getPrecio()
					.equals(oldEntidadInterno.getPrecio());
		}
		if (newEntidadInterno.getPrecioTransporte() != null) {
			priceTransportUpdated = !newEntidadInterno.getPrecioTransporte()
					.equals(oldEntidadInterno.getPrecioTransporte());
		}
		if (newEntidadInterno.getPrecioEmpaquetado() != null) {
			pricePackageUpdated = !newEntidadInterno.getPrecioEmpaquetado()
					.equals(oldEntidadInterno.getPrecioEmpaquetado());
		}
		if (newEntidadInterno.getPorcentajeGanancia() != null)
			priceGainUpdated = !newEntidadInterno.getPorcentajeGanancia()
					.equals(oldEntidadInterno.getPorcentajeGanancia());

		if (priceUpdated || priceTransportUpdated || pricePackageUpdated
				|| priceGainUpdated) {
			newEntidadInterno.setFechaActualizacion(new Date());
		} else {
			newEntidadInterno.setFechaActualizacion(
					oldEntidadInterno.getFechaActualizacion());
		}
	}

	@Override
	public List<CategoryHasUnitDto> getCategoryDtoList() {
		return categoryHasUnitDtoConverter
				.toDtoList(categoryHasUnitRepository.findAll());
	}

	@Override
	public CategoryHasUnitDto updateCategoryHasUnit(CategoryHasUnitDto dto) {
		CategoryHasUnit entity = categoryHasUnitRepository
				.save(categoryHasUnitDtoConverter.toEntidad(dto));
		return categoryHasUnitDtoConverter.toDto(entity);
	}

	@Override
	public List<DatosDistribuidora> getDistribuidoraStatus() {
		return this.datosDistribuidoraRepository
                .findAll()
                .stream()
                .filter(a -> !Constantes.DISTRIBUIDORAS_SIN_USO.contains(a.getDistribuidoraCodigo()))
                .sorted((a,b) -> b.getDistribuidoraCodigo().compareTo(a.getDistribuidoraCodigo()))
                .collect(Collectors.toList());
	}

	@Override
	public void eliminarIndices() {
		productoRepository.deleteAll();
	};
}
