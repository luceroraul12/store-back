package distribuidora.scrapping.services.excel;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.PeticionExcel;
import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.entities.productos.especificos.VillaresEntidad;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VillaresExcelServicioTest {

    @Autowired
    VillaresExcelServicio servicio;

    @Test
    void pruebaRow() throws IOException {
        File file = new File("src/main/resources/static/villares.xls");
        MultipartFile[] multipartFile = new MultipartFile[1];
        multipartFile[0] = new MockMultipartFile("villares", new FileInputStream(file));
        List<VillaresEntidad> productos = servicio.adquirirProductosEntidad(
                PeticionExcel
                        .builder()
                        .excels(multipartFile)
                        .distribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_VILLARES).build());
        List<Producto> productosFinales = servicio.getProductoUtil().arregloToProducto(productos);

        assertEquals(1007, productosFinales.size());
    }
}