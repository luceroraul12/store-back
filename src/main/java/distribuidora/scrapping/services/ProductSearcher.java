package distribuidora.scrapping.services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import distribuidora.scrapping.entities.DatosDistribuidora;
import distribuidora.scrapping.entities.LookupValor;
import distribuidora.scrapping.entities.ProductoEspecifico;
import distribuidora.scrapping.entities.UpdateRequest;
import distribuidora.scrapping.services.general.LookupService;
import distribuidora.scrapping.services.internal.InventorySystem;
import distribuidora.scrapping.util.ProductoUtil;
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
public abstract class ProductSearcher<Entidad extends ProductoEspecifico> {

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
	 * Componente utilitario para realizar conversiones.
	 */
	@Autowired
	private ProductoUtil<Entidad> productoUtil;

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

	public void update(UpdateRequest request) {
		// Busco los datos de la distribuidora
		DatosDistribuidora data = datoDistribuidoraServicio
				.getByCode(distribuidoraCodigo);

	};

	/**
	 * Metodo por el cual se inicia el proceso de busqueda de datos en el
	 * proceso especifico. Luego de la adquisicion de datos, la misma se guarda
	 * en en las colecciones correspondientes.
	 * 
	 * @param elementoAuxiliar
	 *            Clase del elemento que tiene los elementos especificos
	 *            necesarios
	 * @throws IOException
	 * @see BusquedorPorExcel
	 * @see BusquedorPorWebScrapping
	 * @see ProductSearcher#adquirirProductosEntidad(Auxiliar elementoAuxiliar)
	 * @see ProductSearcher#actualizarProductosEnTodasLasColecciones(List
	 *      productos)
	 */
	public void generarProductosEntidadYActualizarCollecciones(
			UpdateRequest request) throws IOException {
		List<Entidad> productosProcesados = adquirirProductosEntidad(request);
		actualizarProductosEnTodasLasColecciones(productosProcesados);
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
	protected abstract List<Entidad> adquirirProductosEntidad(
			UpdateRequest request) throws IOException;

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
			List<Entidad> productos) {

		this.productoServicio.actualizarProductosPorDistribuidora(
				productoUtil.arregloToProducto(productos),
				this.distribuidoraCodigo);
		// Intento actualizar los productos internos a los productos de la
		inventorySystemService.actualizarPreciosAutomatico();

		// Valido cuantos productos existen luego de agregar nuevos
		Integer size = productoServicio
				.countProductosByDistribuidoraCode(distribuidoraCodigo);

		LookupValor lvDistribuidora = lookupService
				.getLookupValueByCode(distribuidoraCodigo);
		DatosDistribuidora data = datoDistribuidoraServicio
				.getByCode(distribuidoraCodigo);
		data.setFechaActualizacion(new Date());
		data.setCantidadDeProductosAlmacenados(size);

		this.datoDistribuidoraServicio.actualizarDatos(data);

	}
}
