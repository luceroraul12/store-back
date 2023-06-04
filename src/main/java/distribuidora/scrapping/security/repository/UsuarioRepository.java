package distribuidora.scrapping.security.repository;

import distribuidora.scrapping.security.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
    UsuarioEntity findByUsuario(String username);

}
