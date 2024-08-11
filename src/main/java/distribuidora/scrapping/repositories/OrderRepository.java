package distribuidora.scrapping.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import distribuidora.scrapping.entities.customer.Cart;

public interface OrderRepository extends JpaRepository<Cart, Integer>{

}
