package distribuidora.scrapping.services.internal;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.dto.CategoryDto;
import distribuidora.scrapping.dto.DatosDistribuidoraDto;
import distribuidora.scrapping.dto.ProductoInternoDto;
import distribuidora.scrapping.entities.Category;
import distribuidora.scrapping.entities.Client;
import distribuidora.scrapping.entities.ProductoInterno;
import distribuidora.scrapping.entities.Presentation;
import distribuidora.scrapping.repositories.DatosDistribuidoraRepository;
import distribuidora.scrapping.repositories.postgres.CategoryHasUnitRepository;
import distribuidora.scrapping.repositories.postgres.ExternalProductRepository;
import distribuidora.scrapping.repositories.postgres.ProductoInternoRepository;
import distribuidora.scrapping.services.CategoryService;
import distribuidora.scrapping.services.PresentationService;
import distribuidora.scrapping.services.UsuarioService;
import distribuidora.scrapping.util.converters.CategoryDtoConverter;
import distribuidora.scrapping.util.converters.DatosDistribuidoraConverter;
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
	private DatosDistribuidoraRepository datosDistribuidoraRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private DatosDistribuidoraConverter datosDistribuidorConverter;

	@Autowired
	private UsuarioService userService;

	@Autowired
	private CategoryDtoConverter categoryDtoConverter;

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private PresentationService unitService;

	@Override
	public int actualizarPreciosAutomatico() {
		// llamado a las bases de datos para obtener los productos especificos e
		// internos
		List<ProductoInterno> productoInternos = productoInternoRepository.getProductosReferenciados();
		Date now = new Date();

		// Recorro cada producto y le agrego el precio external si corresponde
		DecimalFormat df = new DecimalFormat("#.00");
		for (ProductoInterno p : productoInternos) {
			Double externalPrice = p.getExternalProduct().getPrice();
			Double originalPrice = Double.valueOf(df.format(p.getPrecio()));
			if (externalPrice != null && externalPrice > 0.0) {
				Double externalPriceFormated = Double.valueOf(df.format(externalPrice));
				// Solo voy a actualizar los precios de los productos cuando el
				// precio de la vinculacion sea diferente ya que significaria
				// que actualizo el excel o la pagina
				if (!originalPrice.equals(externalPriceFormated)) {
					p.setPrecio(Double.valueOf(df.format(externalPrice)));
					p.setFechaActualizacion(new Date());
				}
			}
		}

		// Persisto los cambios
		productoInternos = productoInternoRepository.saveAll(productoInternos);

		return productoInternos.size();
	}

	@Override
	public ProductoInternoDto crearProducto(ProductoInternoDto dto) throws Exception {
		if (dto.getId() != null)
			return null;

		if (dto.getCategory() == null)
			throw new Exception("Es necesario enviar los datos de la categoría");

		Category category = categoryService.getById(dto.getCategory().getId());

		ProductoInterno producto = productoInternoConverter.toEntidad(dto);
		producto.setCategory(category);

		producto.setFechaCreacion(new Date());
		Client client = usuarioService.getCurrentClient();
		producto.setClient(client);
		Presentation presentation = unitService.getById(dto.getPresentation().getId());
		producto.setPresentation(presentation);
		ProductoInterno productoGuardado = productoInternoRepository.save(producto);
		return productoInternoConverter.toDto(productoGuardado);
	}

	@Override
	public ProductoInternoDto modificarProducto(ProductoInternoDto dto) throws Exception {
		if (dto.getId() == null)
			return null;

		ProductoInterno oldEntidadInterno = productoInternoRepository.getReferenceById(dto.getId());

		if (oldEntidadInterno == null)
			throw new Exception("No existe producto a actualizar");

		Client currentClient = usuarioService.getCurrentClient();

		if (!oldEntidadInterno.getClient().getId().equals(currentClient.getId()))
			throw new Exception("El producto que quiere modificar pertenece a otra tienda.");

		ProductoInterno newEntidadInterno = productoInternoConverter.toEntidad(dto);
		Presentation presentation = unitService.getById(dto.getPresentation().getId());
		newEntidadInterno.setPresentation(presentation);
		newEntidadInterno.setClient(currentClient);

		newEntidadInterno.setFechaCreacion(oldEntidadInterno.getFechaCreacion());
		verifyAndUpdateDateModified(oldEntidadInterno, newEntidadInterno);

		ProductoInterno productoGuardado = productoInternoRepository.save(newEntidadInterno);

		dto = productoInternoConverter.toDto(productoGuardado);

		return dto;
	}

	@Override
	public List<ProductoInternoDto> eliminarProductos(List<Integer> productoInternoIds) {
		List<ProductoInterno> productosEncontrados = productoInternoRepository.getProductosPorIds(productoInternoIds);
		List<Integer> productoIdsEncontrados = productosEncontrados.stream().map(ProductoInterno::getId)
				.collect(Collectors.toList());
		productoInternoRepository.deleteAllById(productoIdsEncontrados);
		return productoInternoConverter.toDtoList(productosEncontrados);
	}

	@Override
	public List<ProductoInternoDto> getProductos(String search) throws Exception {
		Integer clientId = usuarioService.getCurrentClient().getId();
		// Convierto search en mayuscula
		if (StringUtils.isNotEmpty(search))
			search = search.toUpperCase();
		else
			search = null;
		List<ProductoInterno> productos = productoInternoRepository.getAllProductosByUserIdAndSearch(clientId, search);
		return productoInternoConverter.toDtoList(productos);
	}

	@Override
	public List<ProductoInternoDto> updateManyProducto(List<ProductoInternoDto> dtos) throws Exception {
		List<ProductoInternoDto> resultado = new ArrayList<>();
		for (ProductoInternoDto dto : dtos) {
			resultado.add(modificarProducto(dto));
		}
		// retorno todos
		return resultado;
	}

	/**
	 * Encargado de verificar si hay cambios en alguno tipo de producto como: precio
	 * base precio de transporte precio de empaquetado porcentaje de ganancia
	 *
	 * Para poder decidir cuando debe actualizar la fecha de actualizacion
	 * 
	 * @param oldEntidadInterno
	 * @param newEntidadInterno
	 */
	private void verifyAndUpdateDateModified(ProductoInterno oldEntidadInterno, ProductoInterno newEntidadInterno) {
		boolean priceUpdated = false;
		boolean priceTransportUpdated = false;
		boolean pricePackageUpdated = false;
		boolean priceGainUpdated = false;
		if (newEntidadInterno.getPrecio() != null) {
			priceUpdated = !newEntidadInterno.getPrecio().equals(oldEntidadInterno.getPrecio());
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

		if (priceUpdated || priceTransportUpdated || pricePackageUpdated || priceGainUpdated) {
			newEntidadInterno.setFechaActualizacion(new Date());
		} else {
			newEntidadInterno.setFechaActualizacion(oldEntidadInterno.getFechaActualizacion());
		}
	}

	@Override
	public List<CategoryDto> getCategoryDtoList() {
		Integer clientId = userService.getCurrentClient().getId();
		return categoryDtoConverter.toDtoList(categoryHasUnitRepository.findCategoriesByClientId(clientId));
	}

	@Override
	public CategoryDto updateCategoryHasUnit(CategoryDto dto) {
		Category entity = categoryHasUnitRepository.save(categoryDtoConverter.toEntidad(dto));
		return categoryDtoConverter.toDto(entity);
	}

	@Override
	public List<DatosDistribuidoraDto> getDistribuidoraStatus() {
		return datosDistribuidorConverter.toDtoList(datosDistribuidoraRepository.findActives());
	}

	@Override
	public void eliminarIndices() {
		productoRepository.deleteAll();
	}

	@Override
	public boolean existsProducts(List<Integer> productIds) {
		int databaseProductSize = productoInternoRepository.countProductsByIds(productIds);
		return productIds.size() == databaseProductSize;
	}

	@Override
	public List<ProductoInterno> getProductByIds(List<Integer> productIds) {
		return productoInternoRepository.findAllById(productIds);
	}

}
