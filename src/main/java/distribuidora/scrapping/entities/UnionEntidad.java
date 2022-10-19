package distribuidora.scrapping.entities;

import distribuidora.scrapping.enums.Distribuidora;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Document(collection = "union")
/**
 * Es el tipo de dato que se almacena en la base de datos.
 */
public class UnionEntidad<Entidad> {
    @Id
    private String id;
    /**
     * Al ser generico, es importante la Entidad por que es el tipo de dato de cierta distribuidora.
     * @see DonGasparEntidad
     * @see FacundoEntidad
     * @see IndiasEntidad
     * @see LaGranjaDelCentroEntidad
     * @see MelarEntidad
     * @see SudamerikEntidad
     */
    private List<Entidad> datos;
    private LocalDate fechaScrap;
    private Distribuidora distribuidora;
}
