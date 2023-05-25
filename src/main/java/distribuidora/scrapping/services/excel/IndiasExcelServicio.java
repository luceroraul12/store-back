package distribuidora.scrapping.services.excel;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.productos.especificos.IndiasEntidad;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

@Service
public class IndiasExcelServicio extends BusquedorPorExcel<IndiasEntidad> {

    @Override
    boolean esRowValido(Row row) {
        boolean resultado = false;
        try{
            if (row.getCell(1).getCellType().equals(CellType.STRING)){
                if (row.getCell(2).getCellType().equals(CellType.NUMERIC)){
                    if (row.getCell(3).getCellType().equals(CellType.STRING)){
                        resultado = true;
                    }
                }
            }
        } catch (Exception ignored){
        }
        return resultado;
    }

    @Override
    protected void initImplementacion() {
        setDistribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_INDIAS);
    }
    
  
}
