package distribuidora.scrapping.services;

import java.util.List;

import distribuidora.scrapping.dto.DiscountDto;
import distribuidora.scrapping.entities.Discount;

public interface DiscountService {

	List<Discount> getDiscountsByIds(List<Integer> discountIds);

	DiscountDto createUpdateDiscount(DiscountDto dto) throws Exception;

	Integer deleteDiscountById(Integer id) throws Exception;

	List<DiscountDto> getClientDiscounts();

}
