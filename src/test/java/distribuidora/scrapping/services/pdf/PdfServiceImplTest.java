package distribuidora.scrapping.services.pdf;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.itextpdf.text.DocumentException;

@SpringBootTest
class PdfServiceImplTest {

    @Autowired
    PdfService service;


    @Test
    void generatePdf() throws DocumentException, IOException {
        service.generatePdf(null);
    }


}