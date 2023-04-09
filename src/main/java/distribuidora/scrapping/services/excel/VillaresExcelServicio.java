package distribuidora.scrapping.services.excel;

import distribuidora.scrapping.entities.productos.especificos.VillaresEntidad;
import distribuidora.scrapping.enums.Distribuidora;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

@Service
public class VillaresExcelServicio extends BusquedorPorExcel<VillaresEntidad>{
    @Override
    protected void initImplementacion() {
        setDistribuidora(Distribuidora.VILLARES);
    }

    @Override
    boolean esRowValido(Row row) {
        return row != null && row.getCell(1) != null
            ? row.getCell(1).getCellType().equals(CellType.NUMERIC)
                : false;
    }
}
