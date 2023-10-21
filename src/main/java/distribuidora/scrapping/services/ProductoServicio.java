package distribuidora.scrapping.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.entities.ExternalProduct;
import distribuidora.scrapping.repositories.postgres.ProductoRepository;

/**
 * Encargada de la collecion de productos finales.
 */
@Service
public class ProductoServicio {

	@Autowired
	ProductoRepository productoRepository;

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
		Date date = new Date();
		productos.forEach(p -> p.setDate(date));
		// Hago la validacion de productos existentes
		List<ExternalProduct> productExisted = getProductosByDistribuidoraCodigo(
				distribuidoraCodigo);
		List<ExternalProduct> productToUpdate = new ArrayList();
		List<ExternalProduct> productToNew = new ArrayList<>();
		for (ExternalProduct p : productos) {
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
		
		// Persisto los datos
		productoRepository.saveAll(productToNew);
		productoRepository.saveAll(productToUpdate);
	}

	public List<ExternalProduct> obtenerTodosLosProductosAlmacenados() {
		return this.productoRepository.findAll();
	}
	private void eliminarProductosPorDistribuidora(String distribuidoraCodigo) {
		this.productoRepository
				.deleteAllByDistribuidoraCodigo(distribuidoraCodigo);
	}

	private List<ExternalProduct> getProductosByDistribuidoraCodigo(
			String distribuidoraCodigo) {
		return productoRepository.findByDistribuidoraCodigo(distribuidoraCodigo);
	}
	public ExternalProduct getProductoByDistribuidoraCodigoAndId(
			String distribuidoraCodigo, String idReferencia) {
		return this.productoRepository.findByDistribuidoraCodigoAndId(
				distribuidoraCodigo, idReferencia);
	}
	
	public Integer countProductosByDistribuidoraCode(String distribuidoraCode){
		return getProductosByDistribuidoraCodigo(distribuidoraCode).size();
	}
}
