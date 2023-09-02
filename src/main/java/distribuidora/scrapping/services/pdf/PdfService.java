package distribuidora.scrapping.services.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface PdfService {
    static String FILE = "src/main/resources/static/ejemplo.pdf";
    static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

    void generatePdf(HttpServletResponse response) throws IOException, DocumentException;

    void addTitlePage(Document document)
            throws DocumentException, IOException;

    void addContent(Document document) throws DocumentException;
}
