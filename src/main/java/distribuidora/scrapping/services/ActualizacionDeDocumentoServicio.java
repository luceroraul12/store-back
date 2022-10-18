package distribuidora.scrapping.services;

import distribuidora.scrapping.entities.PeticionFrontEndDocumento;
import distribuidora.scrapping.enums.Distribuidora;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;
@Service
public class ActualizacionDeDocumentoServicio {
    @Autowired
    IndiasServicio indiasServicio;

    public void recibirDocumento(PeticionFrontEndDocumento documento) throws IOException {
        Distribuidora distribuidora = documento.getDistribuidora();
        MultipartFile[] archivosOrigen = documento.getExcels();

        System.out.println(distribuidora);
        indiasServicio.obtenerProductos(documento.getExcels()).forEach(System.out::println);
    }

    private void leerExcels(MultipartFile[] archivosOrigen) {
        Stream.of(archivosOrigen).forEach(doc -> {
            System.out.println(doc.getOriginalFilename());
        });
    }
}
