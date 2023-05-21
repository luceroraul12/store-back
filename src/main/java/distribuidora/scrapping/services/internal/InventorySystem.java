package distribuidora.scrapping.services.internal;

import java.util.List;

import org.springframework.stereotype.Service;

import distribuidora.scrapping.entities.ProductoEspecifico;
import distribuidora.scrapping.entities.ProductoInterno;

@Service
public interface InventorySystem {
	
	int actualizarPreciosAutomatico();
	
	void actualizarPrecioConProductosEspecificos(List<ProductoEspecifico> especificos, List<ProductoInterno> internos);
	
}
