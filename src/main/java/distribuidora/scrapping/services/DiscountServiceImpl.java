package distribuidora.scrapping.services;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.dto.DiscountDto;
import distribuidora.scrapping.entities.Client;
import distribuidora.scrapping.entities.Discount;
import distribuidora.scrapping.repositories.DiscountRepository;
import distribuidora.scrapping.util.converters.DiscountDtoConverter;

@Service
public class DiscountServiceImpl implements DiscountService {

	@Autowired
	DiscountRepository discountRepository;

	@Autowired
	UsuarioService userService;

	@Autowired
	CartService cartService;

	@Autowired
	DiscountDtoConverter discountDtoConverter;

	@Override
	public List<Discount> getDiscountsByIds(List<Integer> discountIds) {
		return discountRepository.findAllById(discountIds);
	}

	@Override
	public DiscountDto createUpdateDiscount(DiscountDto dto) throws Exception {
		if (StringUtils.isEmpty(dto.getName()))
			throw new Exception("El descuento debe tener nombre");
		if (dto.getPercentageValue() == null && dto.getPlainValue() == null)
			throw new Exception("Es obligatorio el valor del descuento");
		
		Discount e = null;
		if(dto.getId() != null) {
			e = discountRepository.findById(dto.getId()).orElse(discountDtoConverter.toEntidad(dto));
			e.setDescription(dto.getDescription());
			e.setName(dto.getName());
			e.setPercentageValue(dto.getPercentageValue());
			e.setPlainValue(dto.getPlainValue());
		}
		else 
			e = discountDtoConverter.toEntidad(dto);
		Client client = userService.getCurrentClient();
		e.setClient(client);
		e = discountRepository.save(e);
		return discountDtoConverter.toDto(e);
	}

	@Override
	public Integer deleteDiscountById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		// me fijo que no haya pedidos realizados con este descuento
		if (cartService.hasCartsByDiscountId(id))
			throw new Exception("Existen ventas realizadas con el descuento que se intenta eliminar.");

		discountRepository.deleteById(id);
		return id;
	}

	@Override
	public List<DiscountDto> getClientDiscounts() {
		// TODO Auto-generated method stub
		Integer clientId = userService.getCurrentClient().getId();
		return discountDtoConverter.toDtoList(discountRepository.findByClientId(clientId));
	}

}
