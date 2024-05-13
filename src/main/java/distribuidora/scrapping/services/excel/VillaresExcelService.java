package distribuidora.scrapping.services.excel;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.DatosDistribuidora;
import distribuidora.scrapping.entities.ExternalProduct;

@Service
public class VillaresExcelService extends ProductSearcherExcel {
	@Override
	boolean esRowValido(Row row) {
		return row != null && row.getCell(1) != null
				&& row.getCell(1).getCellType().equals(CellType.NUMERIC);
	}

	@Override
	public void setCodes() {
		setDistribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_VILLARES);
	}

	@Override
	protected ExternalProduct convertirRowEnProductoEspecifico(Row row,
			DatosDistribuidora data) {
		// TODO Auto-generated method stub
		return null;
	}
}
