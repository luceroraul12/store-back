package distribuidora.scrapping.security.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class RolEntity implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "codigo", nullable = false)
    private String codigo;
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Override
    public String getAuthority() {
        return codigo;
    }
}
