package distribuidora.scrapping.services.pdf;

import java.io.IOException;
import java.text.DecimalFormat;
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

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.CategoryHasUnit;
import distribuidora.scrapping.entities.Client;
import distribuidora.scrapping.entities.LookupValor;
import distribuidora.scrapping.entities.ProductoInterno;
import distribuidora.scrapping.entities.ProductoInternoStatus;
import distribuidora.scrapping.repositories.postgres.CategoryHasUnitRepository;
import distribuidora.scrapping.repositories.postgres.ProductoInternoStatusRepository;
import distribuidora.scrapping.services.ClientDataService;

@Service
public class PdfServiceImpl implements PdfService {

	@Autowired
	private CategoryHasUnitRepository categoryHasUnitRepository;
	@Autowired
	private ProductoInternoStatusRepository productoInternoStatusRepository;

	@Autowired
	private ClientDataService clientDataService;

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

		Client data = clientDataService.getById(1);

		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy",
				new Locale("es", "ES"));
		String dateConverted = sdf.format(new Date());

		Paragraph preface = new Paragraph();
		// We add one empty line
		addEmptyLine(preface, 1);
		// Lets write a big header
		Paragraph p = new Paragraph(data.getName().toUpperCase(), catFont);
		p.setAlignment(Element.ALIGN_CENTER);
		preface.add(p);
		p = new Paragraph(dateConverted, smallBold);
		p.setAlignment(Element.ALIGN_CENTER);
		preface.add(p);
		// addEmptyLine(preface, 1);

		// Chunk link = null;

		preface.add(new Paragraph("Datos de contacto".toUpperCase(), subFont));

		preface.add(generateParagraphLink("Direccion", data.getAddress(),
				data.getAddressLink()));
		preface.add(generateParagraphLink("Telefono", data.getPhone(),
				data.getPhoneLink()));
		preface.add(generateParagraphLink("Instagram", data.getInstagram(),
				data.getInstagramLink()));
		preface.add(generateParagraphLink("Facebook", data.getFacebook(),
				data.getFacebookLink()));

		document.add(preface);
	}

	private Paragraph generateParagraphLink(String label, String name,
			String link) {
		Chunk result = new Chunk(String.format("%s - %s", label, name),
				smallFont);
		result.setAnchor(link);
		return new Paragraph(result);
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
					listItem.add(generatePriceWithUnitLogic(
							productoInternoStatus, lvUnit));
					list.add(listItem);
				}
				document.add(list);
			}
		}
	}

	/**
	 * Encargado de generar el comparator final. El orden propuesto fue hay
	 * stock, no es unidad, nombre ascendente
	 * 
	 * @return
	 */
	private Comparator<ProductoInternoStatus> orderProductoInternoStatusList() {
		// Comparator auxiliar por check stock
		Comparator<ProductoInternoStatus> compartorByHasStock = (a, b) -> b
				.getHasStock().compareTo(a.getHasStock());
		// Comparator auxiliar por nombre de productos
		Comparator<ProductoInternoStatus> comparatorByProductName = (a, b) -> a
				.getProductoInterno().getNombre()
				.compareToIgnoreCase(b.getProductoInterno().getNombre());
		// Comparator auxiliar por flag de unidad
		Comparator<ProductoInternoStatus> comparatorByIsUnit = (a, b) -> a
				.getIsUnit().compareTo(b.getIsUnit());

		// Hago que primero haga por nombre, luego por flag y por ultimo de
		// nuevo nombre
		Comparator<ProductoInternoStatus> resultComparator = compartorByHasStock
				.thenComparing(comparatorByIsUnit)
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

	/**
	 * Genera el precio del producto basado en - precio base - precio de flete -
	 * precio de empaquetado - porcentaje de ganancia
	 * 
	 * @param p
	 * @return
	 */
	@Override
	public Integer generateBasePrice(ProductoInterno p) {
		int result;
		double precio = p.getPrecio() != null ? p.getPrecio() : 0;
		double transporte = p.getPrecioTransporte() != null
				? p.getPrecioTransporte()
				: 0.0;
		double empaquetado = p.getPrecioEmpaquetado() != null
				? p.getPrecioEmpaquetado()
				: 0.0;
		double ganancia = (100 + (p.getPorcentajeGanancia() != null
				? p.getPorcentajeGanancia()
				: 0)) / 100;
		double impuesto = (100 + (p.getPorcentajeImpuesto() != null
				? p.getPorcentajeImpuesto()
				: 0)) / 100;
		double precioPorcentual = precio * ganancia * impuesto;
		result = (int) (precioPorcentual + transporte + empaquetado + ganancia);
		return result;
	}

	/**
	 * Toma el resultado de {@link #generateBasePrice(ProductoInterno)} y le
	 * aplica la logica de las unidades. Existen los siguientes casos -
	 * Categoria unidad (no importa unidad del producto): devuelve el precio
	 * base - Categoria fraccionada y producto unidad: devuelve precio base -
	 * Categoria fraccionada y producto fraccionado: devuelve precio fraccionado
	 * 
	 * @param productoInternoStatus
	 * @param lvUnit
	 * @return
	 */
	private String generatePriceWithUnitLogic(
			ProductoInternoStatus productoInternoStatus, LookupValor lvUnit) {
		DecimalFormat df = new DecimalFormat("#.00");
		double basePrice = generateBasePrice(
				productoInternoStatus.getProductoInterno());
		Double result;
		boolean isCategoryUnit = lvUnit.getCodigo()
				.equals(Constantes.LV_MEDIDAS_VENTAS_1U);
		boolean isProductUnit = productoInternoStatus.getIsUnit();
		boolean hasStock = productoInternoStatus.getHasStock();

		// En caso de que el producto se encuentre marcado sin stock,
		// directamente retorno eso
		if (!hasStock) {
			return "SIN STOCK";
		}

		// si la categoria esta marcada como unidad solo retorno el precio
		if (isCategoryUnit || isProductUnit) {
			result = basePrice;
			// en caso contrario tengo que reducir el precio a la fraccion
			// especificada por
			// la unidad
		} else {
			result = basePrice * Double.parseDouble(lvUnit.getValor());
		}

		return df.format(result);
	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
}
