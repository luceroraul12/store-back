package distribuidora.scrapping.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;

import distribuidora.scrapping.dto.CartDto;

public interface CartService {

	List<CartDto> createFinalizedCart(List<CartDto> data) throws Exception;

	Page<CartDto> getCartsPage(Integer personId, LocalDate dateFrom, LocalDate dateTo, Integer page, Integer size);

	void deleteById(Integer id);

	boolean hasCartByCustomerId(Integer id);

	boolean hasCartsByDiscountId(Integer id);
}
