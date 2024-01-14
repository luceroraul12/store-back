package distribuidora.scrapping.services;

import java.util.List;

import distribuidora.scrapping.dto.OrderDto;

public interface OrderService {

	/**
	 * Es para crear ordenes de compras visto desde el cliente final
	 * @param order
	 * @return
	 * @throws Exception
	 */
	OrderDto createOrder(OrderDto order) throws Exception;

	/**
	 * Es para obtener el historial de ordenes realizadas en cierta tienda
	 * @param storeCode
	 * @param username
	 * @return
	 */
	List<OrderDto> getMyOrders(String storeCode, String username);
	
	/**
	 * Es para que luego de {@link #createOrder(OrderDto)} la tienda pueda validar el pedido en precios, productos y cantidades
	 * @param order
	 * @return
	 */
	OrderDto authorizeOrder(OrderDto order);
	
	/**
	 * Es para que luego de {@link #authorizeOrder(OrderDto)} el cliene abone el dinero y se descuente las cantidades del producto
	 * @param order
	 * @return
	 */
	OrderDto finalizeOrder(OrderDto order);

}
