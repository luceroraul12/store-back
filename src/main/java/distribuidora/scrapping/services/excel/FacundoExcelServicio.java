package distribuidora.scrapping.services.excel;

import distribuidora.scrapping.entities.productos.especificos.FacundoEntidad;
import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.enums.Distribuidora;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FacundoExcelServicio extends BusquedorPorExcel<FacundoEntidad> {



    @Override
    boolean esRowValido(Row row) {
        boolean resultado = false;
        try{
            if (row.getCell(0).getCellType().equals(CellType.STRING)){
                if (row.getCell(1).getCellType().equals(CellType.STRING)){
                    if (row.getCell(2).getCellType().equals(CellType.STRING)){
                        if (contieneDouble(row.getCell(3))){
                            if (contieneDouble(row.getCell(4))){
                                resultado = true;
                            }
                        }
                    }
                }
            }
        } catch (Exception ignored){
        }
        return resultado;
    }

    private boolean contieneDouble(Cell celda) {
        return celda.getCellType() != CellType.STRING;
    }

    @Override
    protected void initImplementacion() {
        setDistribuidora(Distribuidora.FACUNDO);
    }
}
