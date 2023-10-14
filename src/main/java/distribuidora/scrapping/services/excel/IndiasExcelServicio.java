package distribuidora.scrapping.services.excel;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.productos.especificos.IndiasEntidad;
import distribuidora.scrapping.enums.TipoDistribuidora;

@Service
public class IndiasExcelServicio extends BusquedorPorExcel<IndiasEntidad> {

    @Override
    boolean esRowValido(Row row) {
        boolean resultado = false;
        boolean rowGeneral = false;
        boolean rowEspecifico = false;

        try {
            rowGeneral = row.getCell(1).getCellType().equals(CellType.STRING)
                    && (row.getCell(3).getCellType().equals(CellType.STRING) || row.getCell(3).getCellType().equals(CellType.NUMERIC))
                    && row.getCell(4).getCellType().equals(CellType.STRING)
                    && row.getCell(6).getCellType().equals(CellType.NUMERIC);
        } catch (Exception exception) {
        }

        try {
            rowEspecifico = row.getCell(1).getCellType().equals(CellType.STRING)
                    && (row.getCell(2).getCellType().equals(CellType.STRING) || row.getCell(2).getCellType().equals(CellType.NUMERIC))
                    && row.getCell(3).getCellType().equals(CellType.STRING)
                    && row.getCell(4).getCellType().equals(CellType.NUMERIC);
        } catch (Exception exception) {}
;
        resultado = rowGeneral || rowEspecifico;
        return resultado;
    }

    @Override
    protected void initImplementacion() {
        setDistribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_INDIAS);
        setTipoDistribuidora(TipoDistribuidora.EXCEL);
    }
    
  
}
