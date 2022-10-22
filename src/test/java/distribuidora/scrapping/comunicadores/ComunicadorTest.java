package distribuidora.scrapping.comunicadores;

import distribuidora.scrapping.entities.Peticion;
import distribuidora.scrapping.entities.PeticionExcel;
import distribuidora.scrapping.entities.PeticionWebScrapping;
import distribuidora.scrapping.enums.Distribuidora;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ComunicadorTest {
    @Autowired
    Comunicador comunicador;

    static Disposable disposable;

    /**
     * pruebas para usar un subject para varias cosas al mismo tiempo
     */
    @Test
    void verificacionMultiple() {
        AtomicInteger resultado = new AtomicInteger();
        PublishSubject<Peticion> subject = comunicador.getDisparadorActualizacion();
        subject
                .filter( peticion -> peticion.getClass() == PeticionWebScrapping.class)
                .cast(PeticionWebScrapping.class)
                .subscribe( peticionWebScrapping -> {
                    if ( peticionWebScrapping.getDistribuidora() != null){
                        resultado.set(1);
                    } else {
                        resultado.set(2);
                    }
                });
        subject
                .filter( peticionExcel -> peticionExcel.getClass() == PeticionExcel.class)
                .subscribe( peticionExcel -> resultado.set(3));


        Peticion peticionWSTodos = PeticionWebScrapping.builder().build();
        Peticion peticionWSIndividual = PeticionWebScrapping.builder()
                .distribuidora(Distribuidora.MELAR)
                .build();
        Peticion peticionEIndividual = PeticionExcel.builder()
                .distribuidora(Distribuidora.INDIAS)
                .build();
        subject.onNext(peticionWSTodos);
        assertEquals(2, resultado.get());
        subject.onNext(peticionWSIndividual);
        assertEquals(1, resultado.get());
        subject.onNext(peticionEIndividual);
        assertEquals(3, resultado.get());

    }
}