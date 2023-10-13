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
    public ProductoInternoStatusDto toDto(ProductoInternoStatus productoInternoStatus) {
        ProductoInternoStatusDto productoInternoStatusDto = new ProductoInternoStatusDto();
        productoInternoStatusDto.setId(productoInternoStatus.getId());
        productoInternoStatusDto.setProductoInterno(productoInternoConverter.toDto(productoInternoStatus.getProductoInterno()));
        productoInternoStatusDto.setHasStock(productoInternoStatus.getHasStock());
        productoInternoStatusDto.setIsUnit(productoInternoStatus.getIsUnit());
        return productoInternoStatusDto;
    }

    @Override
    public ProductoInternoStatus toEntidad(ProductoInternoStatusDto productoInternoStatusDto) {
        ProductoInternoStatus productoInternoStatus = new ProductoInternoStatus();
        productoInternoStatus.setId(productoInternoStatusDto.getId());
        productoInternoStatus.setProductoInterno(productoInternoConverter.toEntidad(productoInternoStatusDto.getProductoInterno()));
        productoInternoStatus.setIsUnit(productoInternoStatusDto.getIsUnit());
        productoInternoStatus.setHasStock(productoInternoStatusDto.getHasStock());
        return productoInternoStatus;
    }
}
