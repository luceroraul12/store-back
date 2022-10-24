package distribuidora.scrapping.services.excel;

import distribuidora.scrapping.entities.productos.especificos.IndiasEntidad;
import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.enums.Distribuidora;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class IndiasExcelServicio extends BusquedorPorExcel<IndiasEntidad> {

    @Override
    boolean esRowValido(Row row) {
        boolean resultado = false;
        try{
            if (row.getCell(1).getCellType().equals(CellType.STRING)){
                if (row.getCell(2).getCellType().equals(CellType.NUMERIC)){
                    if (row.getCell(3).getCellType().equals(CellType.STRING)){
                        if (row.getCell(4).getCellType().equals(CellType.NUMERIC)){
                            resultado = true;
                        }
                    }
                }
            }
        } catch (Exception ignored){
        }
        return resultado;
    }

    @Override
    IndiasEntidad mapearRowPorProducto(Row row) {
        return IndiasEntidad.builder()
                .distribuidora(getDistribuidora())
                .rubro(row.getCell(1).toString())
                .codigo((int) row.getCell(2).getNumericCellValue())
                .descripcion(row.getCell(3).toString())
                .precio(row.getCell(4).getNumericCellValue())
                .build();
    }

    @Override
    protected void initImplementacion() {
        setDistribuidora(Distribuidora.INDIAS);
    }
}
