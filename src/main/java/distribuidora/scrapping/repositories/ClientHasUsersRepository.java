package distribuidora.scrapping.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import distribuidora.scrapping.entities.ClientHasUsers;

public interface ClientHasUsersRepository extends JpaRepository<ClientHasUsers, Integer> {
	
	@Query("""
			SELECT chu 
			FROM ClientHasUsers chu
				INNER JOIN chu.user u
			WHERE u.id = :userId
			""")
	ClientHasUsers findByClientId(Integer userId);

	@Query("""
			SELECT c.id
			FROM ClientHasUsers chu
				INNER JOIN chu.user u
				INNER JOIN chu.client c
			WHERE u.usuario = :username
			""")
	Integer getClientIdByUsername(String username);
}
