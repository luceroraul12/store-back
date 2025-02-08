package distribuidora.scrapping.services;

import java.util.List;

import distribuidora.scrapping.dto.CartDto;

public interface CartService {

	List<CartDto> createFinalizedCart(List<CartDto> data) throws Exception;

	List<CartDto> getCarts();
}
