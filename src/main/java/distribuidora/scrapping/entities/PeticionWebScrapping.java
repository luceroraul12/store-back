package distribuidora.scrapping.entities;

import distribuidora.scrapping.enums.Distribuidora;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
public class PeticionWebScrapping extends Peticion {

    @Builder
    public PeticionWebScrapping(Distribuidora distribuidora) {
        super(distribuidora);
    }
}
