package distribuidora.scrapping.util.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import distribuidora.scrapping.dto.ProductoInternoStatusDto;
import distribuidora.scrapping.entities.ProductoInternoStatus;

@Component
public class ProductoInternoStatusConverter extends Converter<ProductoInternoStatus, ProductoInternoStatusDto>{

    @Autowired
    private ProductoInternoConverter productoInternoConverter;

    @Override
    public ProductoInternoStatusDto toDto(ProductoInternoStatus e) {
        ProductoInternoStatusDto dto = new ProductoInternoStatusDto();
        dto.setId(e.getId());
        dto.setProductoInterno(productoInternoConverter.toDto(e.getProductoInterno()));
        dto.setHasStock(e.getHasStock());
        dto.setIsUnit(e.getIsUnit());
        dto.setAmount(e.getAmount());
        return dto;
    }

    @Override
    public ProductoInternoStatus toEntidad(ProductoInternoStatusDto dto) {
        ProductoInternoStatus e = new ProductoInternoStatus();
        e.setId(dto.getId());
        e.setProductoInterno(productoInternoConverter.toEntidad(dto.getProductoInterno()));
        e.setIsUnit(dto.getIsUnit());
        e.setHasStock(dto.getHasStock());
        e.setAmount(dto.getAmount());
        return e;
    }
}
