package distribuidora.scrapping.services.excel;

import distribuidora.scrapping.comunicadores.Comunicador;
import distribuidora.scrapping.entities.PeticionExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Servicio encargado de actualizaciones de base de dato del tipo excel.
 */
@Service
public class ActualizacionPorDocumentoServicio {
    @Autowired
    private Comunicador comunicador;

    public void recibirDocumento(PeticionExcel peticion) throws IOException {
        this.comunicador.getDisparadorActualizacion().onNext(peticion);
    }
}
