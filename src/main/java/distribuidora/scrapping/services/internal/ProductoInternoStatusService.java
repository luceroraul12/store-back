package distribuidora.scrapping.services.internal;

import distribuidora.scrapping.dto.ProductoInternoStatusDto;
import distribuidora.scrapping.repositories.postgres.ProductoInternoStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface ProductoInternoStatusService {

    List<ProductoInternoStatusDto> getAll();

    ProductoInternoStatusDto update(ProductoInternoStatusDto dto);
}
