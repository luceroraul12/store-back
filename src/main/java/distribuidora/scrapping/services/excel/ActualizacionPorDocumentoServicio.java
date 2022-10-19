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
    private IndiasServicio indiasServicio;
    @Autowired
    private FacundoServicio facundoServicio;

    public void recibirDocumento(PeticionFrontEndDocumento documento) throws IOException {
        Distribuidora distribuidora = documento.getDistribuidora();

        System.out.println(distribuidora);


        identificarDistribuidorayEjecutar(documento).forEach(System.out::println);
    }


    private Collection<?> identificarDistribuidorayEjecutar(PeticionFrontEndDocumento documento) throws IOException {

        switch (documento.getDistribuidora()){
            case FACUNDO: {
                facundoServicio.actualizarProductosEnBaseDeDatos(
                        facundoServicio.obtenerProductosApartirDeExcels(documento.getExcels())
                );
                return facundoServicio.obtenerProductosApartirDeExcels(documento.getExcels());
            }
            case INDIAS: {
                indiasServicio.actualizarProductosEnBaseDeDatos(
                        indiasServicio.obtenerProductosApartirDeExcels(documento.getExcels())
                );
                return indiasServicio.obtenerProductosApartirDeExcels(documento.getExcels());
            }
        }
        return null;
    }
}
