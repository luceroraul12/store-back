package distribuidora.scrapping.services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import distribuidora.scrapping.entities.DatosDistribuidora;
import distribuidora.scrapping.entities.ExternalProduct;
import distribuidora.scrapping.entities.LookupValor;
import distribuidora.scrapping.entities.UpdateRequest;
import distribuidora.scrapping.services.general.LookupService;
import distribuidora.scrapping.services.internal.InventorySystem;
import lombok.Data;

/**
 * Clase padre de todos los servicios de tipos de busqueda. Contiene metodos
 * sobre la base de datos y el abstract para una entidadEspecifica.
 * 
 * @param <Entidad>
 *            necesario para saber el tipo de distribuidora
 * @param <Auxiliar>
 *            clase con los datos necesarios para poder comenzar busqueda
 */
@Data
public abstract class ProductSearcher {

	/**
	 * Es la enumercacion que identifica a cada implementacion de este servicio.
	 * Toda implementacion utilizada debe tenerlo seteado.
	 */
	private String distribuidoraCodigo;
	/**
	 * Es el identificador del tipo de busqueda. Toda implementacion utilizada
	 * debe tenerlo seteado.
	 */
	private LookupValor tipoDistribuidora;

	private LocalDate fechaUltimaActualizacion;

	/**
	 * Servicio basado en Productos finales.
	 */
	@Autowired
	private ExternalProductService productoServicio;

	@Autowired
	private DatoDistribuidoraServicio datoDistribuidoraServicio;

	@Autowired
	private InventorySystem inventorySystemService;

	@Autowired
	private LookupService lookupService;

	@PostConstruct
	public abstract void setCodes();

	public void update(UpdateRequest request) throws IOException {
		// Busco los datos de la distribuidora
		DatosDistribuidora data = datoDistribuidoraServicio
				.getByCode(distribuidoraCodigo);

		setTipoDistribuidora(data.getDistribuidora());
		processRequest(request, data);
	};

	public void processRequest(UpdateRequest request, DatosDistribuidora data)
			throws IOException {
		List<ExternalProduct> productosProcesados = adquirirProductosEntidad(
				request, data);

		actualizarProductosEnTodasLasColecciones(productosProcesados, data);
	}

	/**
	 * Metodo a implementar por cada clase de tipo de busqueda. Este metodo
	 * permite unicamente la adquisicion de productos de cierta entidad y solo
	 * eso.
	 * 
	 * @param elementoAuxiliar
	 * @return lista de productos
	 * @throws IOException
	 */
	protected abstract List<ExternalProduct> adquirirProductosEntidad(
			UpdateRequest request, DatosDistribuidora data) throws IOException;

	/**
	 * Elimina los datos almacenados de cierta distribuidora y vuelve a guardar
	 * con datos nuevos. Esto se realiza en la coleccion Entidad Especifica como
	 * en la de Productos
	 * 
	 * @param productos
	 *            de ciertan entidad // * @see
	 *            BuscadorDeProductos#almacenarProductosEspecificos(List)
	 */
	public void actualizarProductosEnTodasLasColecciones(
			List<ExternalProduct> productos, DatosDistribuidora data) {
		this.productoServicio.actualizarProductosPorDistribuidora(productos,
				data);
		// Intento actualizar los productos internos a los productos de la
		inventorySystemService.actualizarPreciosAutomatico();
		// Valido cuantos productos existen luego de agregar nuevos
		Integer size = productoServicio
				.countProductosByDistribuidoraCode(distribuidoraCodigo);
		data.setFechaActualizacion(new Date());
		data.setCantidadDeProductosAlmacenados(size);

		this.datoDistribuidoraServicio.save(data);

	}
}
