package distribuidora.scrapping.security.entity;

import lombok.Data;

@Data
public class UsuarioDto {
    private Integer id;
    private String username;
    private String password;
}
