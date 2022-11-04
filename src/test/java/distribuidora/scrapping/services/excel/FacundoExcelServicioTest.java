package distribuidora.scrapping.services.excel;

import distribuidora.scrapping.entities.productos.especificos.FacundoEntidad;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FacundoExcelServicioTest {

    @Autowired
    FacundoExcelServicio servicio;

    @Test
    void probarCategoriaRenglonExcel() throws IOException {
        File file = new File("src/main/resources/static/facundo.xlsx");
        MultipartFile[] multipartFile = new MultipartFile[1];
        multipartFile[0] = new MockMultipartFile("facundo", new FileInputStream(file));
        List<FacundoEntidad> productos = servicio.obtenerProductosApartirDeExcels(multipartFile);

        assertEquals("FRUTOS SECOS", productos.get(1).getCategoriaRenglon());
    }
}