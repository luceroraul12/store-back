package distribuidora.scrapping.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.entities.ExternalProduct;
import distribuidora.scrapping.entities.LookupValor;
import distribuidora.scrapping.repositories.postgres.ExternalProductRepository;
import distribuidora.scrapping.services.general.LookupService;
import io.jsonwebtoken.lang.Collections;

/**
 * Encargada de la collecion de productos finales.
 */
@Service
public class ExternalProductService {

	@Autowired
	ExternalProductRepository productoRepository;
	
	@Autowired
	LookupService lookupService;

	/**
	 * Actualiza los productos de cierta distribuidora. Almacena productos, en
	 * caso de que hayan existentes, los borra y guarda los nuevos
	 * 
	 * @param productos
	 *            productos que se quiere almacenar.
	 * @param distribuidora
	 *            distribuidora con la que se quiere trabajar.
	 */
	public void actualizarProductosPorDistribuidora(List<ExternalProduct> productos,
			String distribuidoraCodigo) {
		//TODO: Adaptar a SQL
		Date date = new Date();
		productos.forEach(p -> p.setDate(date));
		// Hago la validacion de productos existentes
		List<ExternalProduct> productExisted = getByDistribuidoraCodeAndProductCode(
				distribuidoraCodigo, null);
		List<ExternalProduct> productToUpdate = new ArrayList();
		List<ExternalProduct> productToNew = new ArrayList<>();
		LookupValor lvDistribuidora = lookupService.getLookupValueByCode(distribuidoraCodigo);
		for (ExternalProduct p : productos) {
			// Actualizo el lookup a cada producto
			p.setDistribuidora(lvDistribuidora);
			boolean isRepeated = false;
			for (ExternalProduct pE : productExisted) {
				if(p.getTitle().equals(pE.getTitle())) {
					// Actualizo al antiguo producto, los valores nuevos
					pE.setDate(date);
					pE.setPrice(p.getPrecioPorCantidadEspecifica());
					// Agrego al listado
					productToUpdate.add(pE);
					isRepeated = true;
				}
			}
			if(!isRepeated)
				productToNew.add(p);
		}
		// En una unica lista meto todos los productos y los persisto
		productToNew.addAll(productToUpdate);
		productoRepository.saveAll(productToNew);
	}

	public List<ExternalProduct> getBySearch(String search) {
		return this.productoRepository.findBySearch(search);
	}

	public List<ExternalProduct> getByDistribuidoraCodeAndProductCode(
			String distribuidoraCodigo, String idReferencia) {
		return this.productoRepository.findByDistribuidoraCodeAndProductCode(
				distribuidoraCodigo, idReferencia);
	}

	public Integer countProductosByDistribuidoraCode(
			String distribuidoraCodigo) {
		return null;
	}
}
