package distribuidora.scrapping.services.pdf;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import distribuidora.scrapping.dto.BasePriceDto;
import distribuidora.scrapping.entities.Category;
import distribuidora.scrapping.entities.Client;
import distribuidora.scrapping.entities.ProductoInterno;
import distribuidora.scrapping.repositories.postgres.CategoryHasUnitRepository;
import distribuidora.scrapping.services.ConfigService;
import distribuidora.scrapping.services.UsuarioService;
import distribuidora.scrapping.services.general.CONFIG;
import distribuidora.scrapping.services.internal.InventorySystem;
import distribuidora.scrapping.util.CalculatorUtil;

@Service
public class PdfServiceImpl implements PdfService {

	@Autowired
	private CategoryHasUnitRepository categoryHasUnitRepository;
	@Autowired
	private InventorySystem inventoryService;

	@Autowired
	ConfigService configService;

	@Autowired
	UsuarioService userService;
	
	@Autowired
	CalculatorUtil calculatorUtil;

	private void generatePdf(HttpServletResponse response, Integer clientId) throws Exception {
		// generacion del pdf
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());

		// Variables sobre la fecha del PDF
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:s", new Locale("es", "ES"));
		String dateConverted = sdf.format(new Date());

		Client client = userService.getCurrentClient();

		document.open();
		addMetaData(document, client);
		addTitlePage(document, client);
		addContent(document, writer, dateConverted, clientId);
		document.close();
	}

	private void addMetaData(Document document, Client client) {
		document.addTitle(String.format("Catalogo %s", client.getName()));
		document.addSubject("Mis productos");
		document.addKeywords(String.format("%s, Negocio, Productos", client.getName()));
		document.addAuthor(client.getName());
		document.addCreator("Lucero Raul");
	}

	public void addTitlePage(Document document, Client client) throws DocumentException, IOException {
		Paragraph preface = new Paragraph();
		// We add one empty line
		addEmptyLine(preface, 1);
		// Pruebas de logo del cliente
		Paragraph p = null;
		try {
			String path = configService.getByCode(CONFIG.IMAGE_FILE_PATH).getValue();
			Image imageLogo = Image.getInstance(String.format("%s/%s", path, client.getFilenameLogo()));
			imageLogo.setAlignment(Element.ALIGN_CENTER);
			imageLogo.setSpacingAfter(0);
			imageLogo.setSpacingBefore(0);
			document.add(imageLogo);
		} catch (Exception e) {
			// En caso de que falle le agrego el datos string del mismo
			p = new Paragraph(client.getName().toUpperCase(), catFont);
			p.setAlignment(Element.ALIGN_CENTER);
			preface.add(p);
		}
		preface.add(p);
		// addEmptyLine(preface, 1);

		// Chunk link = null;

		preface.add(new Paragraph("Datos de contacto".toUpperCase(), subFont));

		preface.add(generateParagraphLink("Direccion", client.getAddress(), client.getAddressLink()));
		preface.add(generateParagraphLink("Telefono", client.getPhone(), client.getPhoneLink()));
		preface.add(generateParagraphLink("Instagram", client.getInstagram(), client.getInstagramLink()));
		preface.add(generateParagraphLink("Facebook", client.getFacebook(), client.getFacebookLink()));

		document.add(preface);
	}

	private Paragraph generateParagraphLink(String label, String name, String link) {
		Chunk result = new Chunk(String.format("%s - %s", label, name), smallFont);
		result.setAnchor(link);
		return new Paragraph(result);
	}

	public void addContent(Document document, PdfWriter writer, String dateConverted, Integer clientId)
			throws Exception {
		List<Category> categories = categoryHasUnitRepository.findAll();

		// TODO: Voy a comentarlo para ver que dice el cliente, en caso que lo
		// quiera de nuevo lo vuelvo a colocar
		// Paragraph title = new Paragraph("Categorias".toUpperCase(), catFont);
		// title.setAlignment(Element.ALIGN_CENTER);
		// document.add(title);

		// busco los prodcutos
		List<ProductoInterno> productoInternosStatus = inventoryService.getProducts(StringUtils.EMPTY);

		for (Category category : categories) {
			// me fijo si la categoria tiene productos asociados
			List<ProductoInterno> productsByActualCategory = productoInternosStatus.stream()
					.filter(p -> p.getCategory().equals(category))
					// ordeno productos por nombre y si hay unidades los dejo al
					// final
					.sorted(orderProductoInternoStatusList()).toList();

			if (CollectionUtils.isNotEmpty(productsByActualCategory)) {
				String categoryDescription = category.getName();
				if (StringUtils.isNotEmpty(category.getDescription()))
					categoryDescription = String.format("%s - %s", categoryDescription, category.getDescription());
				Paragraph categoryParag = new Paragraph(categoryDescription, subFont);
				document.add(categoryParag);

				// Agregar un párrafo vacío para crear espacio
				Paragraph space = new Paragraph(" "); // Un párrafo con un espacio en blanco
				document.add(space);

				PdfPTable table = new PdfPTable(2); // 3 columnas: nombre, separador, precio
				table.setWidthPercentage(100);
				float[] widths = { 0.85f, 0.15f };
				table.setWidths(widths);

				boolean alterColor = false;
				for (ProductoInterno p : productsByActualCategory) {
					BaseColor color = alterColor ? BaseColor.WHITE : BaseColor.LIGHT_GRAY;
					// Nombre del producto
					PdfPCell nameCell = createCell(generateProductName(p), color, PdfPCell.ALIGN_LEFT);
					table.addCell(nameCell);

					// Separador de puntos
//		            PdfPCell separadorCell = new PdfPCell();
//		            Chunk leader = new Chunk(new DottedLineSeparator());
//		            Paragraph separadorParagraph = new Paragraph(leader);
//		            separadorCell.addElement(separadorParagraph);
//		            table.addCell(separadorCell);

					// Precio con unidad
					PdfPCell precioCell = createCell(generatePriceWithUnitLogic(p), color, PdfPCell.ALIGN_RIGHT);
					table.addCell(precioCell);
					alterColor = !alterColor;
				}

				document.add(table);
				checkAndSetPageNumber(document, writer, dateConverted);
			}
		}
	}

	private PdfPCell createCell(String data, BaseColor backgroundColor, int align) {
		PdfPCell cell = new PdfPCell(new Paragraph(data));
		cell.setBackgroundColor(backgroundColor);
		cell.setHorizontalAlignment(align);
		cell.setBorder(PdfPCell.NO_BORDER);
		return cell;
	}

	/**
	 * Valida en que hoja se encuentra en funcion del maximo de hojas y en caso de
	 * que aun no tenga paginado, lo agrega
	 * 
	 * @param document
	 */
	private void checkAndSetPageNumber(Document document, PdfWriter writer, String dateConverted) {
		float yNumberPage = 15f;
		float xNumberPage = document.getPageSize().getWidth() / 2;
		float yDateReference = yNumberPage;
		float xDateReference = 25;
		ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER,
				new Phrase(String.format("Pagina %d", writer.getPageNumber())), xNumberPage, yNumberPage, 0);
		ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT,
				new Phrase(String.format("Fecha de emisión: %s", dateConverted)), xDateReference, yDateReference, 0);
	}

	/**
	 * Encargado de generar el comparator final. El orden propuesto fue hay stock,
	 * no es unidad, nombre ascendente
	 * 
	 * @return
	 */
	private Comparator<ProductoInterno> orderProductoInternoStatusList() {
		// Comparator auxiliar por check stock
		Comparator<ProductoInterno> compartorByHasStock = (a, b) -> b.getAvailable().compareTo(a.getAvailable());
		// Comparator
		Comparator<ProductoInterno> compartorByUnit = (a, b) -> b.getPresentation().getId()
				.compareTo(a.getPresentation().getId());
		// Comparator auxiliar por nombre de productos
		Comparator<ProductoInterno> comparatorByProductName = (a, b) -> a.getNombre()
				.compareToIgnoreCase(b.getNombre());
		// TODO Ver si es necesario ordenar por productos de unidad
//		// Comparator auxiliar por flag de unidad
//		Comparator<ProductoInternoStatus> comparatorByIsUnit = (a, b) -> a.getIsUnit().compareTo(b.getIsUnit());

		Comparator<ProductoInterno> resultComparator = compartorByHasStock.thenComparing(compartorByUnit)
				.thenComparing(comparatorByProductName);

		return resultComparator;
	}

	private String generateProductName(ProductoInterno p) {
		String result;
		String productName = StringUtils
				.capitalize(String.format("[%s] %s", p.getPresentation().getName(), p.getNombre()));
		String description = p.getDescripcion();

		// seteo los datos del producto
		if (StringUtils.isNotEmpty(description)) {
			result = String.format("%s (%s)", productName, description);
		} else {
			result = productName;
		}

		return result;
	}

	/**
	 * Genera el precio del producto basado en - precio base - precio de flete -
	 * precio de empaquetado - porcentaje de ganancia
	 * 
	 * @param p
	 * @return
	 */
	@Override
	public Integer generateBasePrice(ProductoInterno p) {
		BasePriceDto base = calculatorUtil.getBasePrice(p);
		return base.getPrice().intValue();
	}

	@Override
	public int round(int value) {
		int multiple = 50;
		// Recupero la cantidad multiplo
		int eachMultiple = Math.floorDiv(value, multiple);

		int resultBase = eachMultiple * multiple;
		int resultBaseNext = (eachMultiple + 1) * multiple;
		// Rangos
		Range<Integer> rangeOne = Range.between(resultBase, resultBase + 30);
		Range<Integer> rangeTwo = Range.between(resultBase + 31, resultBase + 70);
		Range<Integer> rangeThree = Range.between(resultBase + 71, resultBase + 99);
		// Si el valor es menor de 50, retorno 50
		int result = 50;
		if (value < result)
			return result;

		// Devuelve base
		if (rangeOne.contains(value) || rangeThree.contains(value)) {
			result = resultBase;
			// Devuelve el base intermedio
		} else if (rangeTwo.contains(value)) {
			result = resultBaseNext;
		}
		return result;
	}

	/**
	 * Toma el resultado de {@link #generateBasePrice(ProductoInterno)} y le aplica
	 * la logica de las unidades. Existen los siguientes casos - Categoria unidad
	 * (no importa unidad del producto): devuelve el precio base - Categoria
	 * fraccionada y producto unidad: devuelve precio base - Categoria fraccionada y
	 * producto fraccionado: devuelve precio fraccionado
	 * 
	 * @param productoInternoStatus
	 * @param lvUnit
	 * @return
	 */
	private String generatePriceWithUnitLogic(ProductoInterno productoInternoStatus) {
		double price = generateBasePrice(productoInternoStatus);

		// En caso de que el producto se encuentre marcado sin stock,
		// directamente retorno eso
		if (!productoInternoStatus.getAvailable()) {
			return "SIN STOCK";
		}

		return String.valueOf(round((int) price));
	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	@Override
	public void getPdfByClientId(HttpServletResponse response, Integer clientId) throws Exception {
		response.setContentType("application/pdf");
		generatePdf(response, clientId);
	}
}
