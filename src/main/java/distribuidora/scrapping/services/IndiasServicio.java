package distribuidora.scrapping.services;

import distribuidora.scrapping.entities.IndiasEntidad;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

@Service
public class IndiasServicio extends ExtractorDeProductosExcel<IndiasEntidad>{
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
                .rubro(row.getCell(1).toString())
                .codigo((int) row.getCell(2).getNumericCellValue())
                .descripcion(row.getCell(3).toString())
                .precio(row.getCell(4).getNumericCellValue())
                .build();
    }
}
