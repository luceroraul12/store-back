package distribuidora.scrapping.comunicadores;

import distribuidora.scrapping.entities.Peticion;
import io.reactivex.rxjava3.subjects.PublishSubject;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Clase destinada a contener Subject y Observables.
 */
@Data
@Component
public class Comunicador {

    private PublishSubject<Peticion> disparadorActualizacion = PublishSubject.create();
}
