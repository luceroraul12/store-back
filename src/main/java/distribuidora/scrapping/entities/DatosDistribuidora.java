package distribuidora.scrapping.entities;

import distribuidora.scrapping.enums.Distribuidora;
import distribuidora.scrapping.enums.TipoDistribuidora;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class DatosDistribuidora {
    private Distribuidora distribuidora;
    private TipoDistribuidora tipo;
    private String fechaActualizacion;
}
