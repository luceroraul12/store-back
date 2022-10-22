package distribuidora.scrapping.services.excel;

import distribuidora.scrapping.comunicadores.Comunicador;
import distribuidora.scrapping.entities.PeticionExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ActualizacionPorDocumentoServicio {
    @Autowired
    private IndiasExcelServicio indiasExcelServicio;
    @Autowired
    private FacundoExcelServicio facundoExcelServicio;
    @Autowired
    private Comunicador comunicador;

    public void recibirDocumento(PeticionExcel peticion) throws IOException {
        this.comunicador.getDisparadorActualizacion().onNext(peticion);
    }
    private void identificarDistribuidorayEjecutar(PeticionExcel documento) throws IOException {
        switch (documento.getDistribuidora()){
            case FACUNDO: {
                facundoExcelServicio.generarProductosEntidadYActualizarCollecciones(documento);
                break;
            }
            case INDIAS: {
                indiasExcelServicio.generarProductosEntidadYActualizarCollecciones(documento);
                break;
            }
        }
    }
}
