package distribuidora.scrapping.services.excel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.entities.productos.especificos.VillaresEntidad;
import distribuidora.scrapping.util.VillaresUtil;

@ExtendWith(MockitoExtension.class)
class VillaresExcelServicioTest {

    @InjectMocks
    VillaresExcelServicio servicio;

    @Spy
    VillaresUtil villaresUtil;

    List<VillaresEntidad> productos;
    @BeforeEach
    void setings() throws IOException {
        servicio.initImplementacion();

        File file = new File("src/main/resources/static/villares.xls");
        MultipartFile[] multipartFile = new MultipartFile[1];
        multipartFile[0] = new MockMultipartFile("villares", new FileInputStream(file));
        productos = servicio.obtenerProductosApartirDeExcels(multipartFile);
    }

    @Test
    void pruebaRow() throws IOException {
        File file = new File("src/main/resources/static/villares.xls");
        MultipartFile[] multipartFile = new MultipartFile[1];
        multipartFile[0] = new MockMultipartFile("villares", new FileInputStream(file));
        List<VillaresEntidad> productos = servicio.obtenerProductosApartirDeExcels(multipartFile);
        List<Producto> productosFinales = villaresUtil.arregloToProducto(productos);

        assertEquals(1007, productosFinales.size());
    }

    @Test
    void buscoProductosExistentes() throws IOException {
        Map<String, Producto> mapProductosFinales = villaresUtil.arregloToProducto(productos).stream()
                .collect(Collectors.toMap(Producto::getId, Function.identity()));

        assertTrue(mapProductosFinales.get("44955R-48").getDescripcion().toUpperCase()
                .contains("Almendra pelada".toUpperCase()));
    }
}