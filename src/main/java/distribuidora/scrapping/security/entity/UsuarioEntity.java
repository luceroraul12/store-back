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
@Table(name = "usuarios")
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "telefono", nullable = false)
    private String telefono;
    @Column(name = "usuario", nullable = false)
    private String usuario;
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
    @ManyToOne
    @JoinColumn(name = "perfil_id")
    private PerfilEntity perfil;
}
