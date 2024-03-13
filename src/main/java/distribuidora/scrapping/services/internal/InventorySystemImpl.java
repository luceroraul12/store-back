package distribuidora.scrapping.services.internal;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.dto.CategoryHasUnitDto;
import distribuidora.scrapping.dto.ProductoInternoDto;
import distribuidora.scrapping.entities.CategoryHasUnit;
import distribuidora.scrapping.entities.DatosDistribuidora;
import distribuidora.scrapping.entities.ProductoInterno;
import distribuidora.scrapping.repositories.DatosDistribuidoraRepository;
import distribuidora.scrapping.repositories.postgres.CategoryHasUnitRepository;
import distribuidora.scrapping.repositories.postgres.ExternalProductRepository;
import distribuidora.scrapping.repositories.postgres.ProductoInternoRepository;
import distribuidora.scrapping.services.ExternalProductService;
import distribuidora.scrapping.services.UsuarioService;
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

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public int actualizarPreciosAutomatico() {
		// llamado a las bases de datos para obtener los productos especificos e
		// internos
		List<ProductoInterno> productoInternos = productoInternoRepository
				.getProductosReferenciados();
		Date now = new Date();

		// Recorro cada producto y le agrego el precio external si corresponde
		DecimalFormat df = new DecimalFormat("#.00");
		for (ProductoInterno p : productoInternos) {
			Double externalPrice = p.getExternalProduct().getPrice();
			if (externalPrice != null && externalPrice > 0.0) {
				p.setPrecio(Double.valueOf(df.format(externalPrice)));
				p.setFechaActualizacion(new Date());
			}
		}

		// Persisto los cambios
		productoInternos = productoInternoRepository.saveAll(productoInternos);

		return productoInternos.size();
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
		verifyAndUpdateDateModified(oldEntidadInterno, newEntidadInterno);

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
	public List<ProductoInternoDto> getProductos() throws Exception {
		Integer clientId = usuarioService.getCurrentClientId();

		List<ProductoInterno> productos = productoInternoRepository
				.getAllProductosByUserId(clientId);
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
	private void verifyAndUpdateDateModified(ProductoInterno oldEntidadInterno,
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
		return this.datosDistribuidoraRepository.findAll().stream()
				.filter(a -> !Constantes.DISTRIBUIDORAS_SIN_USO
						.contains(a.getDistribuidoraCodigo()))
				.sorted((a, b) -> b.getDistribuidoraCodigo()
						.compareTo(a.getDistribuidoraCodigo()))
				.collect(Collectors.toList());
	}

	@Override
	public void eliminarIndices() {
		productoRepository.deleteAll();
	}

	@Override
	public boolean existsProducts(List<Integer> productIds) {
		int databaseProductSize = productoInternoRepository
				.countProductsByIds(productIds);
		return productIds.size() == databaseProductSize;
	}

	@Override
	public List<ProductoInterno> getProductByIds(List<Integer> productIds) {
		return productoInternoRepository.findAllById(productIds);
	}

}
