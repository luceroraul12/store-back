package distribuidora.scrapping.services.internal;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.dto.ProductCustomerDto;
import distribuidora.scrapping.dto.ProductoInternoStatusDto;
import distribuidora.scrapping.entities.Client;
import distribuidora.scrapping.entities.ProductoInternoStatus;
import distribuidora.scrapping.entities.Presentation;
import distribuidora.scrapping.repositories.postgres.CategoryHasUnitRepository;
import distribuidora.scrapping.repositories.postgres.ProductoInternoStatusRepository;
import distribuidora.scrapping.services.CategoryService;
import distribuidora.scrapping.services.UsuarioService;
import distribuidora.scrapping.services.general.LookupService;
import distribuidora.scrapping.util.CalculatorUtil;
import distribuidora.scrapping.util.converters.LookupValueDtoConverter;
import distribuidora.scrapping.util.converters.ProductCustomerDtoConverter;
import distribuidora.scrapping.util.converters.ProductoInternoStatusConverter;

@Service
public class ProductoInternoStatusServiceImp implements ProductoInternoStatusService {

	@Autowired
	ProductoInternoStatusRepository repository;

	@Autowired
	CategoryHasUnitRepository categoryHasUnitRepository;

	@Autowired
	ProductoInternoStatusConverter converter;

	@Autowired
	ProductCustomerDtoConverter productCustomerDtoConverter;

	@Autowired
	LookupValueDtoConverter lookupValueDtoConverter;

	@Autowired
	UsuarioService userService;

	@Autowired
	LookupService lookupService;

	@Autowired
	CalculatorUtil calculatorUtil;

	@Autowired
	CategoryService categoryService;

	@Override
	public List<ProductoInternoStatusDto> getByClientId(Integer clientId) throws Exception {
		if (clientId == null) {
			throw new Exception("La tienda no existe");
		}
		List<ProductoInternoStatusDto> result = converter.toDtoList(repository.findByClientId(clientId));
		configLowAmount(result);
		return result;
	}

	@Override
	public ProductoInternoStatusDto update(ProductoInternoStatusDto dto) {
		ProductoInternoStatus entity = converter.toEntidad(dto);
		dto = converter.toDto(repository.save(entity));
		configLowAmount(dto);
		return dto;
	}

	@Override
	public List<ProductCustomerDto> getProductsForCustomer() {
		Client client = userService.getCurrentClient();
		List<ProductoInternoStatus> entities = repository.findByClientId(client.getId());
		List<ProductCustomerDto> dtos = new ArrayList<ProductCustomerDto>();
		// Tengo que asignar el precio a cada producto en base a las unidades de la
		// categoria
		for (ProductoInternoStatus e : entities) {
			Presentation presentation = e.getProductoInterno().getPresentation();
			ProductCustomerDto dto = productCustomerDtoConverter.toDto(e);
			if (presentation != null) {
				dto.setBasePrices(calculatorUtil.getBasePriceList(e.getProductoInterno()));
			}
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public List<ProductoInternoStatus> getAllByProductIds(List<Integer> productIds) {
		return repository.findAllByProductIds(productIds);
	}

	@Override
	public void saveAll(List<ProductoInternoStatus> productStatus) {
		repository.saveAll(productStatus);
	}

	/**
	 * Me fijo segun una constante si la cantidad de stock es poca o no. Es posible
	 * que esto cambie y base en otra cosa o utilice la base de datos
	 * 
	 * @param result
	 */
	private void configLowAmount(List<ProductoInternoStatusDto> result) {
		result.forEach(pis -> configLowAmount(pis));
	}

	/**
	 * Me fijo segun una constante si la cantidad de stock es poca o no. Es posible
	 * que esto cambie y base en otra cosa o utilice la base de datos
	 * 
	 * @param result
	 */
	private void configLowAmount(ProductoInternoStatusDto dto) {
		int minAmount = 2;
		dto.setLowAmount(dto.getAmount() < minAmount);
	}

	@Override
	public List<ProductoInternoStatus> getAllEntities() {
		return repository.findByClientId(userService.getCurrentClient().getId());
	}
}
