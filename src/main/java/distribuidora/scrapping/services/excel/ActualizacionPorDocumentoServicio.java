package distribuidora.scrapping.services.excel;

import distribuidora.scrapping.entities.PeticionFrontEndDocumento;
import distribuidora.scrapping.enums.Distribuidora;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;

@Service
public class ActualizacionPorDocumentoServicio {
    @Autowired
    private IndiasExcelServicio indiasExcelServicio;
    @Autowired
    private FacundoExcelServicio facundoExcelServicio;

    public void recibirDocumento(PeticionFrontEndDocumento documento) throws IOException {
        Distribuidora distribuidora = documento.getDistribuidora();

        System.out.println(distribuidora);


        identificarDistribuidorayEjecutar(documento).forEach(System.out::println);
    }


    private Collection<?> identificarDistribuidorayEjecutar(PeticionFrontEndDocumento documento) throws IOException {

        switch (documento.getDistribuidora()){
            case FACUNDO: {
                facundoExcelServicio.actualizarProductosEnBaseDeDatos(
                        facundoExcelServicio.obtenerProductosApartirDeExcels(documento.getExcels())
                );
                return facundoExcelServicio.obtenerProductosApartirDeExcels(documento.getExcels());
            }
            case INDIAS: {
                indiasExcelServicio.actualizarProductosEnBaseDeDatos(
                        indiasExcelServicio.obtenerProductosApartirDeExcels(documento.getExcels())
                );
                return indiasExcelServicio.obtenerProductosApartirDeExcels(documento.getExcels());
            }
        }
        return null;
    }
}
