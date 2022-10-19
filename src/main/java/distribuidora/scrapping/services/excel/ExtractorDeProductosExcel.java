package distribuidora.scrapping.services.excel;

import distribuidora.scrapping.entities.PeticionFrontEndDocumento;
import distribuidora.scrapping.services.BusquedaDeProductoPorDistribuidora;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class ExtractorDeProductosExcel<Entidad> extends BusquedaDeProductoPorDistribuidora<Entidad, PeticionFrontEndDocumento> {


    @Override
    protected List<Entidad> trabajarDocumentoyObtenerSusProductos(PeticionFrontEndDocumento elementoAuxiliar) {
        List<Entidad> productosrecolectados;

        try {
            productosrecolectados = new ArrayList<>(obtenerProductosApartirDeExcels(elementoAuxiliar.getExcels()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return productosrecolectados;
    }

    public List<Entidad> obtenerProductosApartirDeExcels(MultipartFile[] excels) throws IOException {
        List<Entidad> productosFinales = new ArrayList<>();
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
                    row.forEach(cell -> expandirValorDeCeldasFusionadas(sheet, cell));

                    productosFinales.addAll(trabajarConRowyObtenerProducto(row));
                }
        );        
        return  productosFinales;
    }


    private void expandirValorDeCeldasFusionadas(Sheet sheet, Cell celda) {
        sheet.getMergedRegions().forEach(
                rango -> {
                    Cell celdaUnica = sheet.getRow(rango.getFirstRow()).getCell(rango.getFirstColumn());
                    if (rango.isInRange(celda)){
                        try {
                            celda.setCellValue(celdaUnica.getStringCellValue());
                        } catch (Exception e) {
                            celda.setCellValue(celdaUnica.getNumericCellValue());
                        }
                    }
                }
        );
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
