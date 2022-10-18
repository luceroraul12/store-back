package distribuidora.scrapping.services;

import distribuidora.scrapping.entities.PeticionFrontEndDocumento;
import distribuidora.scrapping.enums.Distribuidora;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ActualizacionDeDocumentoServicioTest {

    static PeticionFrontEndDocumento peticion;


    @Autowired
    ActualizacionDeDocumentoServicio actualizacionDeDocumentoServicio;

    @BeforeAll
    static void beforeAll() throws IOException {
        Path facundo = Paths.get("src/main/resources/static/Facundo.xls");

        MultipartFile documentoRecibido = new MockMultipartFile("picho", Files.readAllBytes(facundo));

        peticion = PeticionFrontEndDocumento.builder()
                .distribuidora(Distribuidora.SUDAMERIK)
                .excels(documentoRecibido)
                .build();
    }

    @Test
    void recibirDocumento() {
       int resultado = actualizacionDeDocumentoServicio.recibirDocumento(peticion);
       assertEquals(1, resultado);
    }
}