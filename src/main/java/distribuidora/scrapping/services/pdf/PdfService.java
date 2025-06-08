package distribuidora.scrapping.services.pdf;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;

import distribuidora.scrapping.entities.ProductoInterno;

public interface PdfService {
	static String FILE = "src/main/resources/static/ejemplo.pdf";
	static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL,
			BaseColor.RED);
	static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.BOLD);
	static Font smallFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.NORMAL, BaseColor.BLUE);

	Integer generateBasePrice(ProductoInterno p);

	/**
	 * Forma de redondear numeros <br>
	 * <strong>Ejemplos</strong>
	 * <ul>
	 * <li>510...520 es <strong>500</strong></li>
	 * <li>530...570 es <strong>550</strong></li>
	 * <li>580...590 es <strong>600</strong></li>
	 * </ul>
	 * 
	 * 
	 * 
	 * @param result
	 * @return
	 */
	int round(int result);

	void getPdfByClientId(HttpServletResponse response, Integer clientId)
			throws IOException, DocumentException, Exception;
}
