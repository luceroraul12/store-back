package distribuidora.scrapping.services.internal;

import java.util.List;

import distribuidora.scrapping.dto.ProductoInternoStatusDto;

public interface ProductoInternoStatusService {

    List<ProductoInternoStatusDto> getAll();

    ProductoInternoStatusDto update(ProductoInternoStatusDto dto);
}
