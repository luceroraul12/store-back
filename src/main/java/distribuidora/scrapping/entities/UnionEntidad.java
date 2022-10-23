package distribuidora.scrapping.entities;

import distribuidora.scrapping.entities.productos.especificos.*;
import distribuidora.scrapping.enums.Distribuidora;
import distribuidora.scrapping.enums.TipoDistribuidora;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

/**
 * Es el tipo de dato que se almacena en la base de datos.
 */
@Data
@Document(collection = "union")
public class UnionEntidad<Entidad extends ProductoEspecifico> {
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
    private TipoDistribuidora tipoDistribuidora;
    private Integer cantidadDeProductosAlmacenados;
}
