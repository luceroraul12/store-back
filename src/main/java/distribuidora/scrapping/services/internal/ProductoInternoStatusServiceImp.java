package distribuidora.scrapping.services.internal;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.dto.ProductCustomerDto;
import distribuidora.scrapping.dto.ProductoInternoStatusDto;
import distribuidora.scrapping.entities.LookupValor;
import distribuidora.scrapping.entities.ProductoInternoStatus;
import distribuidora.scrapping.repositories.postgres.CategoryHasUnitRepository;
import distribuidora.scrapping.repositories.postgres.ProductoInternoStatusRepository;
import distribuidora.scrapping.util.converters.LookupValueDtoConverter;
import distribuidora.scrapping.util.converters.ProductCustomerDtoConverter;
import distribuidora.scrapping.util.converters.ProductoInternoStatusConverter;

@Service
public class ProductoInternoStatusServiceImp
		implements
			ProductoInternoStatusService {

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

	@Override
	public List<ProductoInternoStatusDto> getAll() {
		return converter.toDtoList(repository.findAll());
	}

	@Override
	public ProductoInternoStatusDto update(ProductoInternoStatusDto dto) {
		ProductoInternoStatus entity = converter.toEntidad(dto);
		return converter.toDto(repository.save(entity));
	}

	@Override
	public List<ProductCustomerDto> getProductsForCustomer() {
		List<ProductoInternoStatus> entities = repository.findAll();
		List<ProductCustomerDto> dtos = productCustomerDtoConverter
				.toDtoList(entities);
		// Busco las relaciones de las categorias con las unidades
		Map<Integer, LookupValor> mapUnitByCategoryId = categoryHasUnitRepository
				.findAll().stream().collect(Collectors
						.toMap(r -> r.getCategory().getId(), r -> r.getUnit()));
		// Recorro cada dto para asignarle su unidad
		for (ProductCustomerDto d : dtos) {
			LookupValor unit = mapUnitByCategoryId
					.getOrDefault(d.getCategory().getId(), null);
			if (unit != null)
				d.setUnitType(lookupValueDtoConverter.toDto(unit));
		}
		return dtos;
	}

	@Override
	public List<ProductoInternoStatus> getAllByProductIds(
			List<Integer> productIds) {
		return repository.findAllByProductIds(productIds);
	}

	@Override
	public void saveAll(List<ProductoInternoStatus> productStatus) {
		repository.saveAll(productStatus);
	}
}
