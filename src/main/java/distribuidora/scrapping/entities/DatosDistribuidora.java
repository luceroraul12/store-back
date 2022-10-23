package distribuidora.scrapping.entities;

import distribuidora.scrapping.enums.Distribuidora;
import distribuidora.scrapping.enums.TipoDistribuidora;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Builder
@Data
@Document(collection = "DatosDistribuidora")
public class DatosDistribuidora {
    @Id
    private String id;
    private Distribuidora distribuidora;
    private TipoDistribuidora tipo;
    private String fechaActualizacion;
    private Integer cantidadDeProductosAlmacenados;
}
