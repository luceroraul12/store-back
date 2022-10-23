package distribuidora.scrapping.services.excel;

import distribuidora.scrapping.comunicadores.Comunicador;
import distribuidora.scrapping.entities.PeticionExcel;
import distribuidora.scrapping.entities.ProductoEspecifico;
import distribuidora.scrapping.enums.TipoDistribuidora;
import distribuidora.scrapping.services.BuscadorDeProductos;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Encargada de configurar la busqueda por medio de documentos excel.
 * @param <Entidad>
 * @see PeticionExcel
 */
public abstract class BusquedorPorExcel<Entidad extends ProductoEspecifico> extends BuscadorDeProductos<Entidad, PeticionExcel> {

    @Autowired
    Comunicador comunicador;
    @Override
    protected List<Entidad> adquirirProductosEntidad(PeticionExcel elementoAuxiliar) {
        List<Entidad> productosrecolectados;
        try {
            productosrecolectados = new ArrayList<>(obtenerProductosApartirDeExcels(elementoAuxiliar.getExcels()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return productosrecolectados;
    }

    @Override
    protected void initTipoDeBusqueda() {
        setTipoDistribuidora(TipoDistribuidora.EXCEL);
        System.out.println("buscadores excel");
        comunicador.getDisparadorActualizacion()
                .filter(comunicadorInformacionAuxiliar
                        -> comunicadorInformacionAuxiliar.getClass() == PeticionExcel.class)
                .cast(PeticionExcel.class)
                .subscribe(
                documento -> {
                    if(documento.getDistribuidora() == getDistribuidora()){
                        System.out.println("actualiza "+ getDistribuidora());
                        this.generarProductosEntidadYActualizarCollecciones(documento);
                    } else {
                        System.out.println("no actualiza "+ getDistribuidora());
                    }
                }
        );
    }

    @Override
    protected void destroy() {
        comunicador.getDisparadorActualizacion().onComplete();
    }

    /**
     * Encargado de obtener la lista de productos en dicha Entidad.
     * hace todos los pasos para convertir y analizar workbooks a partir de Multipart hasta obtener List Entidad
     * @param excels Documentos a los cuales se los va aconvertir en WorkBook
     * @return Lista de productos en entidad especifica
     * @throws IOException
     */
    public List<Entidad> obtenerProductosApartirDeExcels(@NotNull MultipartFile[] excels) throws IOException {
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


    /**
     * Genera todos los sheets que contenga un documento Excel.
     * @param excel un unico documento excel.
     * @return una o varias sheets.
     * @throws IOException
     */
    private ArrayList<Sheet> obtenerSheets(MultipartFile excel) throws IOException {
        Workbook workbook = new HSSFWorkbook(excel.getInputStream());
        ArrayList<Sheet> sheets = new ArrayList<>();
        workbook.forEach(sheets::add);
        return sheets;
    }

    /**
     * Extrae todos los productos del sheet
     * @param sheet es uno solo
     * @return lsita de productos
     */
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

    /**
     * Expande los valores de celdas fusionadas.
     * Cuando un rango de celdas es fusionado, todas muestran el mismo valor. Pero el valor de todas las celdas solo se encuentra en la primera columna primer renglon de un rango de valores fusionados, el resto de celdas del rango tienen valor null.
     * Este metodo se encarga de analizar una celda con respecto a los rangos de fusion para una sheet y en caso de encajar en uno (y no ser col 0 row 0) se le copia el valor correspondiente para que no tenga valor null.
     * @param sheet
     * @param celda
     */
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

    /**
     * Trabaja con un renglon para obtener productos del mismo.
     * Este renglon previamente es pasado por un condicional para indicar si valido realizar la extraccion.
     * @param row del sheet
     * @return lista de productos
     * @see BusquedorPorExcel#esRowValido(Row row)
     */
    private Collection<Entidad> trabajarConRowyObtenerProducto(Row row) {
        Collection<Entidad> productosPorRows = new ArrayList<>();
        if (esRowValido(row)){
            productosPorRows.add(mapearRowPorProducto(row));
        }
        return productosPorRows;
    }

    /**
     * Encargado de verificar si es un renglon valido para mapear.
     * Es necesario debido a que algunos excels estan acompaniado de datos que no son necesarios para la generacion de productos o por que no cumplen con los requisitos minimos necesarios para que sea un producto.
     * @param row renglon a verificar
     * @return booleano
     */
    abstract boolean esRowValido(Row row);

    /**
     * Encargado de mapear un renglon a Producto Entidad.
     * toma todos las celdas de un renglon y las mapea al producto Entidad.
     * @param row del sheet
     * @return producto Entidad
     */
    abstract Entidad mapearRowPorProducto(Row row);


}
