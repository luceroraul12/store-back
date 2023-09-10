package distribuidora.scrapping.services.internal;

import distribuidora.scrapping.dto.ProductoInternoStatusDto;
import java.util.List;

public interface ProductoInternoStatusService {

    List<ProductoInternoStatusDto> getAll();

    ProductoInternoStatusDto update(ProductoInternoStatusDto dto);
}
