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
import java.util.stream.Collectors;

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
        List<FacundoEntidad> vinos = productos.stream()
                .filter(p -> p.getSubcategoria().contains("Vinos")).toList();
        List<FacundoEntidad> verdurasDeshidratadas = productos.stream()
                .filter(p -> p.getSubcategoria().contains("Verduras Deshidratadas")).toList();
        List<FacundoEntidad> celiacos = productos.stream()
                .filter(p -> p.getSubcategoria().contains("Celiacos")).toList();
        List<FacundoEntidad> chocolates = productos.stream()
                .filter(p -> p.getSubcategoria().contains("Chocolates")).toList();

//        FacundoEntidad caso1 = productos.stream()
//                .filter(p -> p.getSubcategoria().equals("Vinos                                                (Vinos)"))
//                .toList()
//                .get(0);
//        FacundoEntidad caso2 = productos.stream()
//                .filter(p -> p.getSubcategoria().equals("Limón (NUEVOS) \"Granix\""))
//                .toList()
//                .get(0);
//        FacundoEntidad caso3 = productos.stream()
//                .filter(p -> p.getSubcategoria().equals("Triturado Premium \"Importado\""))
//                .toList()
//                .get(0);
//        FacundoEntidad caso4 = productos.stream()
//                .filter(p -> p.getSubcategoria().equals("SIN PIEL \"PRODUCCION PROPIA\""))
//                .toList()
//                .get(0);


//        assertEquals("1884 Escorihuela Gascon Malbec_unidad", caso1.getCategoria());
        assertEquals(85, vinos.size());
        assertEquals(50/2, verdurasDeshidratadas.size());
        assertEquals(80/2, celiacos.size());
        assertEquals(76/2, chocolates.size());
//        assertEquals("CEREALES: \"GRANIX\"", caso2.getCategoriaRenglon());
//        assertEquals("Especias Gourmet", caso3.getCategoriaRenglon());
//        assertEquals("Harinas / Avena", caso4.getCategoriaRenglon());
    }
}