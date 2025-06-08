package distribuidora.scrapping.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import distribuidora.scrapping.entities.Config;

public interface ConfigRepository extends JpaRepository<Config, Integer> {
	@Query("""
			select c 
			from Config c
			where c.code = :code
			""")
	Config findByCode(String code);
}
