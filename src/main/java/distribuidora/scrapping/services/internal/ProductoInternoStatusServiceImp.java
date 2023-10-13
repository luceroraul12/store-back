package distribuidora.scrapping.services.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.dto.ProductoInternoStatusDto;
import distribuidora.scrapping.entities.ProductoInternoStatus;
import distribuidora.scrapping.repositories.postgres.ProductoInternoStatusRepository;
import distribuidora.scrapping.util.converters.ProductoInternoStatusConverter;

@Service
public class ProductoInternoStatusServiceImp implements ProductoInternoStatusService {

    @Autowired
    ProductoInternoStatusRepository repository;

    @Autowired
    ProductoInternoStatusConverter converter;

    @Override
    public List<ProductoInternoStatusDto> getAll() {
        return converter.toDtoList(repository.findAll());
    }

    @Override
    public ProductoInternoStatusDto update(ProductoInternoStatusDto dto) {
        ProductoInternoStatus entity = converter.toEntidad(dto);
        return converter.toDto(repository.save(entity));
    }
}
