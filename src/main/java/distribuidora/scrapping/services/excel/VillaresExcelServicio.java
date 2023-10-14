package distribuidora.scrapping.services.excel;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.productos.especificos.VillaresEntidad;
import distribuidora.scrapping.enums.TipoDistribuidora;

@Service
public class VillaresExcelServicio extends BusquedorPorExcel<VillaresEntidad>{
    @Override
    protected void initImplementacion() {
        setDistribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_VILLARES);
        setTipoDistribuidora(TipoDistribuidora.EXCEL);
    }

    @Override
    boolean esRowValido(Row row) {
        return row != null && row.getCell(1) != null && row.getCell(1).getCellType().equals(CellType.NUMERIC);
    }
}
