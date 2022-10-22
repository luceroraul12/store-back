package distribuidora.scrapping.comunicadores;

import distribuidora.scrapping.enums.Distribuidora;
import io.reactivex.rxjava3.subjects.PublishSubject;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Clase destinada a contener Subject y Observables.
 */
@Data
@Component
public class Comunicador {
    private PublishSubject<Boolean> disparadorActualizacionWebScrapping = PublishSubject.create();
    private PublishSubject<Distribuidora> disparadorSelectorImplementacionPorDistribuidora = PublishSubject.create();
}
