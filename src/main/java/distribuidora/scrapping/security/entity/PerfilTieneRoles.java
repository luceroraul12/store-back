package distribuidora.scrapping.security.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

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
