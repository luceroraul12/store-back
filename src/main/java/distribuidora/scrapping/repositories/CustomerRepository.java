package distribuidora.scrapping.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import distribuidora.scrapping.entities.customer.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

}
