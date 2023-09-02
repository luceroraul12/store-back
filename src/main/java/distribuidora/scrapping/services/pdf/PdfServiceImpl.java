package distribuidora.scrapping.services.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import distribuidora.scrapping.entities.LookupValor;
import distribuidora.scrapping.dto.ProductoInternoDto;
import distribuidora.scrapping.services.internal.InventorySystem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class PdfServiceImpl implements PdfService {

    @Autowired
    private InventorySystem inventorySystemService;

    @Override
    public void generatePdf() throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(FILE));
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

            Paragraph category = new Paragraph(lvCategory.getDescripcion(), catFont);
            document.add(category);
            List list = new List();
            for (ProductoInternoDto p : productList) {
                ListItem listItem = new ListItem();
                String label = StringUtils.isNotEmpty(p.getDescripcion())
                        ? String.format("%s (%s)", p.getNombre(), p.getDescripcion())
                        : p.getNombre();
                listItem.add(label);
                listItem.add(leader);
                listItem.add(String.format("ARS %s", p.getPrecio()));
                list.add(listItem);
            }
            document.add(list);


        }
    }

    private static void createTable(Section subCatPart)
            throws BadElementException {
        PdfPTable table = new PdfPTable(3);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        PdfPCell c1 = new PdfPCell(new Phrase("Table Header 1"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 2"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 3"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);

        table.addCell("1.0");
        table.addCell("1.1");
        table.addCell("1.2");
        table.addCell("2.1");
        table.addCell("2.2");
        table.addCell("2.3");

        subCatPart.add(table);

    }

    private static void createList(Section subCatPart) {
        List list = new List(true, false, 10);
        list.add(new ListItem("First point"));
        list.add(new ListItem("Second point"));
        list.add(new ListItem("Third point"));
        subCatPart.add(list);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
