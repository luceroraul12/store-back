package distribuidora.scrapping.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import distribuidora.scrapping.entities.customer.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	@Query("""
			SELECT c 
			FROM Customer c
			WHERE c.username = :username 
			""")
	Customer findByUsername(@Param("username") String username);

}
