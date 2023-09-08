package distribuidora.scrapping.services.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import distribuidora.scrapping.entities.CategoryHasUnit;
import distribuidora.scrapping.entities.LookupValor;
import distribuidora.scrapping.entities.ProductoInterno;
import distribuidora.scrapping.entities.ProductoInternoStatus;
import distribuidora.scrapping.repositories.postgres.CategoryHasUnitRepository;
import distribuidora.scrapping.repositories.postgres.ProductoInternoStatusRepository;
import distribuidora.scrapping.services.internal.InventorySystem;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class PdfServiceImpl implements PdfService {

    @Autowired
    private InventorySystem inventorySystemService;

    @Autowired
    private CategoryHasUnitRepository categoryHasUnitRepository;
    @Autowired
    private ProductoInternoStatusRepository productoInternoStatusRepository;

    @Override
    public void generatePdf(HttpServletResponse response) throws IOException, DocumentException {
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

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", new Locale("es", "ES"));
        String dateConverted = sdf.format(new Date());

        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        Paragraph p = new Paragraph(String.format("PASIONARIA CATALOGO - %s", dateConverted).toUpperCase(), catFont);
        p.setAlignment(Element.ALIGN_CENTER);
        preface.add(p);
        addEmptyLine(preface, 1);

        preface.add(new Paragraph("Datos de contacto".toUpperCase(), subFont));
        addEmptyLine(preface, 1);

        Anchor anchor;

        Image image = Image.getInstance("src/main/resources/static/instagram.png");
        image.scaleToFit(20,20);
        anchor = new Anchor(new Chunk(image, 0, 0, true));
        anchor.setReference(instagram);
        preface.add(anchor);

        image = Image.getInstance("src/main/resources/static/facebook.png");
        image.scaleToFit(20,20);
        anchor = new Anchor(new Chunk(image, 0, 0, true));
        anchor.setReference(facebook);
        preface.add(anchor);

        image = Image.getInstance("src/main/resources/static/whatsapp.png");
        image.scaleToFit(20,20);
        anchor = new Anchor(new Chunk(image, 0, 0, true));
        anchor.setReference(phone);
        preface.add(anchor);

        image = Image.getInstance("src/main/resources/static/gmaps.png");
        image.scaleToFit(20,20);
        anchor = new Anchor(new Chunk(image, 0, 0, true));
        anchor.setReference(address);
        preface.add(anchor);

        document.add(preface);
    }

    @Override
    public void addContent(Document document) throws DocumentException {
        List<CategoryHasUnit> categoryHasUnits = categoryHasUnitRepository.findAll();

        Paragraph title = new Paragraph("Categorias".toUpperCase(),catFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // agrupo por categoria
        Map<String, List<CategoryHasUnit>> mapRelationsByCategorName = new TreeMap<>();
        mapRelationsByCategorName.putAll(categoryHasUnits.stream()
                .collect(Collectors.groupingBy(r -> r.getCategory().getDescripcion())));

        // busco los prodcutos
        List<ProductoInternoStatus> productoInternosStatus = productoInternoStatusRepository.findAll();

        for (Map.Entry<String, List<CategoryHasUnit>> entry : mapRelationsByCategorName.entrySet()) {
            String k = entry.getKey();
            List<LookupValor> v = entry.getValue().stream()
                    .map(CategoryHasUnit::getUnit)
                    .toList();
            // me fijo si la categoria tiene productos asociados
            List<ProductoInternoStatus> productsByActualCategory = productoInternosStatus.stream()
                    .filter(p -> p.getProductoInterno().getLvCategoria().getDescripcion().equals(k))
                    // deben tener stock
                    .filter(p -> p.getHasStock())
                    .toList();

            if (CollectionUtils.isNotEmpty(productsByActualCategory)){
                // agrego datos de la categoria
                Paragraph category = new Paragraph(k, subFont);
                document.add(category);

                // agrego datos de los productos de la categoria

                Chunk leader = new Chunk(new DottedLineSeparator());
                com.itextpdf.text.List list = new com.itextpdf.text.List();
                for (ProductoInternoStatus productoInternoStatus : productsByActualCategory) {
                    // si el producto se encuentra marcado como unidad, debo mostrarlo como UNIDAD
                    ProductoInterno product = productoInternoStatus.getProductoInterno();
                    String productName;
                    if (productoInternoStatus.getIsUnit()){
                        ListItem listItem = new ListItem();
                        productName = generateProductName(product.getNombre(), product.getDescripcion(), "UNIDAD");
                        listItem.add(productName);
                        listItem.add(leader);
                        listItem.add(String.valueOf(generatePrecio(product)));
                        list.add(listItem);
                    } else {
                        // si el producto se encuentra marcado como no unidad, le aplico las unidades de la categoria
                        for (LookupValor unit : v) {
                            ListItem listItem = new ListItem();
                            productName = generateProductName(product.getNombre(), product.getDescripcion(), unit.getDescripcion());
                            listItem.add(productName);
                            listItem.add(leader);
                            listItem.add(String.valueOf(generatePrecio(product, unit.getValor())));
                            list.add(listItem);
                        }
                    }
                }
                document.add(list);
            }
        }
    }

    private String generateProductName(String productName, String descripcion, String unitName) {
        String result;
        if (StringUtils.isNotEmpty(descripcion)){
            result = String.format("%s (%s) - %s", productName, descripcion, unitName);
        } else {
            result = String.format("%s - %s", productName, unitName);
        }
        return result;
    }

    private Double generatePrecio(ProductoInterno p) {
        double result = 0.0;
        double precio = p.getPrecio() != null ? p.getPrecio() : 0.0;
        double transporte = p.getPrecioTransporte() != null ? p.getPrecioTransporte() : 0.0;
        double empaquetado = p.getPrecioEmpaquetado() != null ? p.getPrecioEmpaquetado() : 0.0;
        double ganancia = ((p.getPorcentajeGanancia() != null ? p.getPorcentajeGanancia() : 0.0) / 100) * precio;
        result = precio + transporte + empaquetado + ganancia;
        return result;
    }

    private Double generatePrecio(ProductoInterno p, Double unitPercentage){
        return generatePrecio(p) * unitPercentage;
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
