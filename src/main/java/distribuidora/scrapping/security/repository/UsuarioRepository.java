package distribuidora.scrapping.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import distribuidora.scrapping.security.entity.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
    UsuarioEntity findByUsuario(String username);

}
