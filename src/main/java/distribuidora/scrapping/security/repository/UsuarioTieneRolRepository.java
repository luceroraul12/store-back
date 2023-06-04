package distribuidora.scrapping.security.repository;

import distribuidora.scrapping.security.entity.PerfilTieneRoles;
import distribuidora.scrapping.security.entity.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioTieneRolRepository extends JpaRepository<PerfilTieneRoles, Integer> {

    @Query("SELECT r FROM PerfilTieneRoles ptr " +
            "   INNER JOIN ptr.rol r " +
            "   INNER JOIN ptr.perfil p " +
            "   INNER JOIN UsuarioEntity u ON u.perfil = p " +
            "WHERE u.usuario = :usuario ")
    List<RolEntity> getRolesDelUsuario(@Param("usuario") String usuario);
}
