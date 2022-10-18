package distribuidora.scrapping.services;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public abstract class ExtractorDeProductosExcel<Entidad> {

    public Collection<Entidad> obtenerProductos(MultipartFile[] excels) throws IOException {
        Collection<Entidad> productosFinales = new ArrayList<>();
        ArrayList<Sheet> sheets;
        for (MultipartFile excel : excels) {
            System.out.println(excel.getOriginalFilename());
            sheets = obtenerSheets(excel);
            sheets.forEach(s -> {
                productosFinales.addAll(obtenerProductosPorSheet(s));
            });
        }

        return productosFinales;
    }

    private ArrayList<Sheet> obtenerSheets(MultipartFile excel) throws IOException {
        Workbook workbook = new HSSFWorkbook(excel.getInputStream());
        ArrayList<Sheet> sheets = new ArrayList<>();
        workbook.forEach(sheets::add);
        return sheets;
    }
    private Collection<Entidad> obtenerProductosPorSheet(Sheet sheet){
        Collection<Entidad> productosFinales = new ArrayList<>();
        sheet.forEach(
                row -> {
                    productosFinales.addAll(trabajarConRowyObtenerProducto(row));
                }
        );        
        return  productosFinales;
    }

    private Collection<Entidad> trabajarConRowyObtenerProducto(Row row) {
        Collection<Entidad> productosPorRows = new ArrayList<>();
        if (esRowValido(row)){
            productosPorRows.add(mapearRowPorProducto(row));
        }
        return productosPorRows;
    }

    abstract boolean esRowValido(Row row);

    abstract Entidad mapearRowPorProducto(Row row);


}
