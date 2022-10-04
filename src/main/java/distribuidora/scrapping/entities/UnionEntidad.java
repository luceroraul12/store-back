package distribuidora.scrapping.entities;

import distribuidora.scrapping.enums.Distribuidora;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Document(collection = "union")
public class UnionEntidad<Entidad> {
    @Id
    private String id;
    private List<Entidad> datos;
    private LocalDate fechaScrap;
    private Distribuidora distribuidora;
}
