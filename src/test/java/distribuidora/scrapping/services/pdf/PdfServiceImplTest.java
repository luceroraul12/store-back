package distribuidora.scrapping.services.pdf;

import com.itextpdf.text.DocumentException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class PdfServiceImplTest {

    @Autowired
    PdfService service;


    @Test
    void generatePdf() throws DocumentException, IOException {
        service.generatePdf(null);
    }


}