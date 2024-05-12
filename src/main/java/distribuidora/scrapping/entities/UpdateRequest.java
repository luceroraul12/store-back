package distribuidora.scrapping.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public abstract class UpdateRequest {
    private String distribuidoraCodigo;
}
