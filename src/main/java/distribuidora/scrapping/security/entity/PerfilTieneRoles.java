package distribuidora.scrapping.security.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "perfil_tiene_roles")
public class PerfilTieneRoles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "perfil_id")
    private PerfilEntity perfil;
    @ManyToOne
    @JoinColumn(name = "rol_id")
    private RolEntity rol;

}
