package distribuidora.scrapping.entities;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PeticionWebScrapping extends Peticion {

    @Builder
    public PeticionWebScrapping(String distribuidoraCodigo) {
        super(distribuidoraCodigo);
    }
}
