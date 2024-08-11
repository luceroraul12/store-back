package distribuidora.scrapping.repositories;


import org.springframework.data.jpa.repository.JpaRepository;


import distribuidora.scrapping.entities.customer.CartProduct;

public interface CartProductRepository extends JpaRepository<CartProduct, Integer>{

}
