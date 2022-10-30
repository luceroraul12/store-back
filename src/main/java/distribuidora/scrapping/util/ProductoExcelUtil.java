package distribuidora.scrapping.util;

import distribuidora.scrapping.enums.Distribuidora;
import org.apache.poi.ss.usermodel.Row;

public abstract class ProductoExcelUtil<Entidad> extends ProductoUtil<Entidad>{

    public abstract Entidad convertirRowEnProductoEspecifico(Row row, Distribuidora distribuidora);
}
