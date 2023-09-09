package distribuidora.scrapping.services.pdf;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.CategoryHasUnit;
import distribuidora.scrapping.entities.LookupValor;
import distribuidora.scrapping.entities.ProductoInterno;
import distribuidora.scrapping.entities.ProductoInternoStatus;
import distribuidora.scrapping.repositories.postgres.CategoryHasUnitRepository;
import distribuidora.scrapping.repositories.postgres.ProductoInternoStatusRepository;
import distribuidora.scrapping.services.internal.InventorySystem;

@Service
public class PdfServiceImpl implements PdfService {

	@Autowired
	private InventorySystem inventorySystemService;

	@Autowired
	private CategoryHasUnitRepository categoryHasUnitRepository;
	@Autowired
	private ProductoInternoStatusRepository productoInternoStatusRepository;

	@Override
	public void generatePdf(HttpServletResponse response)
			throws IOException, DocumentException {
		// generacion del pdf
		Document document = new Document();
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();
		addMetaData(document);
		addTitlePage(document);
		addContent(document);
		document.close();
	}

	private static void addMetaData(Document document) {
		document.addTitle("Catalogo Pasionaria");
		document.addSubject("Mis productos");
		document.addKeywords("Pasionaria, Dietetica, Negocio, Productos");
		document.addAuthor("Juan");
		document.addCreator("Lucero Raul");
	}

	@Override
	public void addTitlePage(Document document)
			throws DocumentException, IOException {

		String phone = "https://api.whatsapp.com/send/?phone=542664312837&text&type=phone_number&app_absent=0";
		String instagram = "https://www.instagram.com/pasionaria.vm.sl/";
		String address = "https://goo.gl/maps/4K6m4uivZY6CHYeH7";
		String facebook = "https://www.facebook.com/profile.php?id=100070005324554";

		SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy",
				new Locale("es", "ES"));
		String dateConverted = sdf.format(new Date());

		Paragraph preface = new Paragraph();
		// We add one empty line
		addEmptyLine(preface, 1);
		// Lets write a big header
		Paragraph p = new Paragraph(
				String.format("PASIONARIA CATALOGO - %s", dateConverted)
						.toUpperCase(),
				catFont);
		p.setAlignment(Element.ALIGN_CENTER);
		preface.add(p);
		addEmptyLine(preface, 1);

		preface.add(new Paragraph("Datos de contacto".toUpperCase(), subFont));
		addEmptyLine(preface, 1);

		Anchor anchor;

		Image image = Image
				.getInstance("src/main/resources/static/instagram.png");
		image.scaleToFit(20, 20);
		anchor = new Anchor(new Chunk(image, 0, 0, true));
		anchor.setReference(instagram);
		preface.add(anchor);

		image = Image.getInstance("src/main/resources/static/facebook.png");
		image.scaleToFit(20, 20);
		anchor = new Anchor(new Chunk(image, 0, 0, true));
		anchor.setReference(facebook);
		preface.add(anchor);

		image = Image.getInstance("src/main/resources/static/whatsapp.png");
		image.scaleToFit(20, 20);
		anchor = new Anchor(new Chunk(image, 0, 0, true));
		anchor.setReference(phone);
		preface.add(anchor);

		image = Image.getInstance("src/main/resources/static/gmaps.png");
		image.scaleToFit(20, 20);
		anchor = new Anchor(new Chunk(image, 0, 0, true));
		anchor.setReference(address);
		preface.add(anchor);

		document.add(preface);
	}

	@Override
	public void addContent(Document document) throws DocumentException {
		List<CategoryHasUnit> categoryHasUnits = categoryHasUnitRepository
				.findAll();

		Paragraph title = new Paragraph("Categorias".toUpperCase(), catFont);
		title.setAlignment(Element.ALIGN_CENTER);
		document.add(title);

		// agrupo por categoria
		Map<LookupValor, List<CategoryHasUnit>> mapRelationsByLvCategory = new TreeMap<>(
				(a, b) -> a.getDescripcion().compareTo(b.getDescripcion()));
		mapRelationsByLvCategory.putAll(categoryHasUnits.stream()
				.collect(Collectors.groupingBy(r -> r.getCategory())));

		// busco los prodcutos
		List<ProductoInternoStatus> productoInternosStatus = productoInternoStatusRepository
				.findAll();

		for (Map.Entry<LookupValor, List<CategoryHasUnit>> entry : mapRelationsByLvCategory
				.entrySet()) {
			LookupValor lvCategory = entry.getKey();
			LookupValor lvUnit = entry.getValue().stream()
					.map(CategoryHasUnit::getUnit).findFirst().orElse(null);
			// me fijo si la categoria tiene productos asociados
			List<ProductoInternoStatus> productsByActualCategory = productoInternosStatus
					.stream()
					.filter(p -> p.getProductoInterno().getLvCategoria()
							.equals(lvCategory))
					// deben tener stock
					.filter(p -> p.getHasStock())
					// ordeno productos por nombre y si hay unidades los dejo al
					// final
					.sorted(orderProductoInternoStatusList()).toList();

			if (CollectionUtils.isNotEmpty(productsByActualCategory)) {

				// agrego datos de la categoria
				String categoryDescription = String.format("%s - %s",
						lvCategory.getDescripcion(), lvUnit.getDescripcion());
				Paragraph category = new Paragraph(categoryDescription,
						subFont);
				document.add(category);

				// agrego datos de los productos de la categoria
				Chunk leader = new Chunk(new DottedLineSeparator());
				com.itextpdf.text.List list = new com.itextpdf.text.List();
				for (ProductoInternoStatus productoInternoStatus : productsByActualCategory) {
					ListItem listItem = new ListItem();
					listItem.add(
							generateProductName(productoInternoStatus, lvUnit));
					listItem.add(leader);
					listItem.add(String.valueOf(generatePrecio(
							productoInternoStatus.getProductoInterno(),
							lvUnit)));
					list.add(listItem);
				}
				document.add(list);
			}
		}
	}

	private Comparator<ProductoInternoStatus> orderProductoInternoStatusList() {
		// Comparator auxiliar por nombre de productos
		Comparator<ProductoInternoStatus> comparatorByProductName = (a, b) -> a
				.getProductoInterno().getNombre()
				.compareToIgnoreCase(b.getProductoInterno().getNombre());
		// Comparator auxiliar por flag de unidad
		Comparator<ProductoInternoStatus> comparatorByIsUnit = (a, b) -> a
				.getIsUnit().compareTo(b.getIsUnit());

		// Hago que primero haga por nombre, luego por flag y por ultimo de
		// nuevo nombre
		Comparator<ProductoInternoStatus> resultComparator = comparatorByIsUnit
				.thenComparing(comparatorByProductName);

		return resultComparator;
	}

	private String generateProductName(ProductoInternoStatus productStatus,
			LookupValor lvUnit) {
		String result;
		String productName = org.springframework.util.StringUtils
				.capitalize(productStatus.getProductoInterno().getNombre());
		String description = productStatus.getProductoInterno()
				.getDescripcion();
		boolean isCategoryUnit = lvUnit.getCodigo()
				.equals(Constantes.LV_MEDIDAS_VENTAS_1U);
		boolean isProductUnit = productStatus.getIsUnit();

		// seteo los datos del producto
		if (StringUtils.isNotEmpty(description)) {
			result = String.format("%s (%s)", productName, description);
		} else {
			result = productName;
		}

		// seteo los datos de la categoria en caso de que sea diferente de la
		// unidad
		if (!isCategoryUnit && isProductUnit)
			result = String.format("%s - %s", result,
					Constantes.LV_MEDIDAS_VENTAS_1U_DESCRIPTION);
		return result;
	}

	private Double generateBasePrice(ProductoInterno p) {
		double result = 0.0;
		double precio = p.getPrecio() != null ? p.getPrecio() : 0.0;
		double transporte = p.getPrecioTransporte() != null
				? p.getPrecioTransporte()
				: 0.0;
		double empaquetado = p.getPrecioEmpaquetado() != null
				? p.getPrecioEmpaquetado()
				: 0.0;
		double ganancia = ((p.getPorcentajeGanancia() != null
				? p.getPorcentajeGanancia()
				: 0.0) / 100) * precio;
		result = precio + transporte + empaquetado + ganancia;
		return result;
	}

	private Double generatePrecio(ProductoInterno p, LookupValor lvUnit) {
		double basePrice = generateBasePrice(p);
		double result;
		boolean isCategoryUnit = lvUnit.getCodigo()
				.equals(Constantes.LV_MEDIDAS_VENTAS_1U);
		// si la categoria esta marcada como unidad solo retorno el precio
		if (isCategoryUnit) {
			result = basePrice;
			// en caso contrario tengo que reducir el precio a la fraccion
			// especificada por
			// la unidad
		} else {
			result = basePrice * Double.parseDouble(lvUnit.getValor());;
		}
		return result;
	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
}
