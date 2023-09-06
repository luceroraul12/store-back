package distribuidora.scrapping.services.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import distribuidora.scrapping.entities.CategoryHasUnit;
import distribuidora.scrapping.entities.LookupValor;
import distribuidora.scrapping.dto.ProductoInternoDto;
import distribuidora.scrapping.repositories.postgres.CategoryHasUnitRepository;
import distribuidora.scrapping.services.internal.InventorySystem;
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
        Map<LookupValor, List<CategoryHasUnit>> categoryHasUnitListByCategory = categoryHasUnitRepository.findAll().stream()
                .collect(Collectors.groupingBy(CategoryHasUnit::getCategory));

        Paragraph title = new Paragraph("Categorias".toUpperCase(),subFont);
        title.setAlignment(Element.ALIGN_CENTER);

        Chunk leader = new Chunk(new DottedLineSeparator());

        document.add(title);

        TreeMap<LookupValor, java.util.List<ProductoInternoDto>> mapProductsByCategory = new TreeMap<>(
                (a,b) -> a.getDescripcion().compareTo(b.getDescripcion()));
        mapProductsByCategory.putAll(inventorySystemService.getProductos().stream()
                .collect(Collectors.groupingBy(ProductoInternoDto::getLvCategoria)));

        for (Map.Entry<LookupValor, java.util.List<ProductoInternoDto>> entry : mapProductsByCategory.entrySet()) {
            LookupValor lvCategory = entry.getKey();
            java.util.List<ProductoInternoDto> productList = entry.getValue();
            productList.sort((a,b) -> a.getNombre().compareTo(b.getNombre()));

            Paragraph category = new Paragraph("SIN UNIT", catFont);
            com.itextpdf.text.List list = new com.itextpdf.text.List();
            if (!categoryHasUnitListByCategory.containsKey(lvCategory)){
                category = new Paragraph(lvCategory.getDescripcion()+" unidad", catFont);
                for (ProductoInternoDto p : productList) {
                    ListItem listItem = new ListItem();
                    String label = StringUtils.isNotEmpty(p.getDescripcion())
                            ? String.format("%s (%s)", p.getNombre(), p.getDescripcion())
                            : p.getNombre();
                    listItem.add(label);
                    listItem.add(leader);
                    listItem.add(String.format("ARS %s", generatePrecio(p)));
                    list.add(listItem);
                }
                document.add(category);
                document.add(list);
            } else if(categoryHasUnitListByCategory.containsKey(lvCategory) && categoryHasUnitListByCategory.get(lvCategory).size() == 1) {
                String unit = categoryHasUnitListByCategory.get(lvCategory).get(0).getUnit().getDescripcion();
                category = new Paragraph(lvCategory.getDescripcion()+" "+unit, catFont);
                for (ProductoInternoDto p : productList) {
                    ListItem listItem = new ListItem();
                    String label = StringUtils.isNotEmpty(p.getDescripcion())
                            ? String.format("%s (%s)", p.getNombre(), p.getDescripcion())
                            : p.getNombre();
                    listItem.add(label);
                    listItem.add(leader);
                    listItem.add(String.format("ARS %s", generatePrecio(p)));
                    list.add(listItem);
                }
                document.add(category);
                document.add(list);
            } else if(categoryHasUnitListByCategory.containsKey(lvCategory) && categoryHasUnitListByCategory.get(lvCategory).size() > 1) {
                category = new Paragraph(lvCategory.getDescripcion(), catFont);
                for (ProductoInternoDto p : productList) {
                    for (CategoryHasUnit chu : categoryHasUnitListByCategory.get(lvCategory)) {
                        ListItem listItem = new ListItem();
                        String unitName = chu.getUnit().getDescripcion();
                        Double unitPercentage = chu.getUnit().getValor();
                        String label = (StringUtils.isNotEmpty(p.getDescripcion())
                                ? String.format("%s (%s)", p.getNombre(), p.getDescripcion())
                                : p.getNombre()) + " " + unitName;
                        listItem.add(label);
                        listItem.add(leader);
                        listItem.add(String.format("ARS %s", generatePrecio(p, unitPercentage)));
                        list.add(listItem);
                    }
                }
                document.add(category);
                document.add(list);
            }


        }
    }

    private Double generatePrecio(ProductoInternoDto p) {
        double precio = p.getPrecio() != null ? p.getPrecio() : 0.0;
        double transporte = p.getPrecioTransporte() != null ? p.getPrecioTransporte() : 0.0;
        double empaquetado = p.getPrecioEmpaquetado() != null ? p.getPrecioEmpaquetado() : 0.0;
        double ganancia = ((p.getPorcentajeGanancia() != null ? p.getPorcentajeGanancia() : 0.0) / 100) * precio;
        return precio + transporte + empaquetado + ganancia;
    }

    private Double generatePrecio(ProductoInternoDto p, Double unitPercentage){
        return generatePrecio(p) * unitPercentage;
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
