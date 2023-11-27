package distribuidora.scrapping.util.converters;

import org.springframework.stereotype.Component;

import distribuidora.scrapping.dto.ProductOrderDto;
import distribuidora.scrapping.entities.ProductoInterno;
import distribuidora.scrapping.entities.customer.OrderHasProduct;

@Component
public class OrderHasProductConverter extends Converter<OrderHasProduct, ProductOrderDto>{

	@Override
	public ProductOrderDto toDto(OrderHasProduct e) {
		ProductOrderDto d = new ProductOrderDto();
		d.setId(e.getId());
		d.setProductId(e.getProduct().getId());
		d.setAmount(e.getAmount());
		d.setUnitName(e.getUnitName());
		d.setUnitPrice(e.getUnitPrice());
		d.setUnitValue(e.getUnitValue());
		return d;
	}

	@Override
	public OrderHasProduct toEntidad(ProductOrderDto dto) {
		OrderHasProduct e = new OrderHasProduct();
		e.setId(dto.getId());
		e.setUnitName(dto.getUnitName());
		e.setUnitPrice(dto.getUnitPrice());
		e.setUnitValue(dto.getUnitValue());
		e.setAmount(dto.getAmount());
		ProductoInterno p = new ProductoInterno();
		p.setId(dto.getProductId());
		e.setProduct(p);
		return e;
	}

}
