package distribuidora.scrapping.services;

import distribuidora.scrapping.entities.PeticionFrontEndDocumento;
import distribuidora.scrapping.enums.Distribuidora;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.stream.Stream;
@Service
public class ActualizacionDeDocumentoServicio {

    public int recibirDocumento(PeticionFrontEndDocumento documento) {
        Distribuidora distribuidora = documento.getDistribuidora();
        MultipartFile[] archivosOrigen = documento.getExcels();

        return leerDocumentos(archivosOrigen);
    }

    private int leerDocumentos(MultipartFile[] archivosOrigen) {
        final int[] contador = {0};
        Stream.of(archivosOrigen).forEach(doc -> {
            contador[0]++;
            System.out.println(doc.getOriginalFilename());
        });
        return archivosOrigen.length;
    }
}
