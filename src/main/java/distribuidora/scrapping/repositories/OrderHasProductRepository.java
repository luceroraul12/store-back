package distribuidora.scrapping.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import distribuidora.scrapping.entities.customer.OrderHasProduct;

public interface OrderHasProductRepository extends JpaRepository<OrderHasProduct, Integer>{

}
