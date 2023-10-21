package distribuidora.scrapping.services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import distribuidora.scrapping.entities.DatosDistribuidora;
import distribuidora.scrapping.entities.ProductoEspecifico;
import distribuidora.scrapping.enums.TipoDistribuidora;
import distribuidora.scrapping.services.excel.BusquedorPorExcel;
import distribuidora.scrapping.services.internal.InventorySystem;
import distribuidora.scrapping.services.webscrapping.BusquedorPorWebScrapping;
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
public abstract class BuscadorDeProductos<Entidad extends ProductoEspecifico, Auxiliar> {

	/**
	 * Es la enumercacion que identifica a cada implementacion de este servicio.
	 * Toda implementacion utilizada debe tenerlo seteado.
	 */
	private String distribuidoraCodigo;
	/**
	 * Es el identificador del tipo de busqueda. Toda implementacion utilizada
	 * debe tenerlo seteado.
	 */
	private TipoDistribuidora tipoDistribuidora;

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

	/**
	 * Servicio que hace referencia a las diferentes implementaciones para
	 * productos especificos.
	 */
	@Autowired
	private ProductoEspecificoServicio<Entidad> productoEspecificoServicio;

	@Autowired
	private DatoDistribuidoraServicio datoDistribuidoraServicio;

	@Autowired
	private InventorySystem inventorySystemService;

	/**
	 * Metodo post constructor para poder setear atributos de cada clase.
	 */
	@PostConstruct
	private void init() {
		initImplementacion();
		verificarExistenciaEnBaseDeDatosEspecifica();
	}

	/**
	 * Metodo iniciador para la implementacion.
	 */
	protected abstract void initImplementacion();

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
	 * @see BuscadorDeProductos#adquirirProductosEntidad(Auxiliar
	 *      elementoAuxiliar)
	 * @see BuscadorDeProductos#actualizarProductosEnTodasLasColecciones(List
	 *      productos)
	 */
	public void generarProductosEntidadYActualizarCollecciones(
			Auxiliar elementoAuxiliar) throws IOException {
		List<Entidad> productosProcesados = adquirirProductosEntidad(
				elementoAuxiliar);
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
			Auxiliar elementoAuxiliar) throws IOException;

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
		Integer size = productoServicio.countProductosByDistribuidoraCode(distribuidoraCodigo);

		this.datoDistribuidoraServicio
				.actualizarDatos(Collections.singletonList(DatosDistribuidora
						.builder().distribuidoraCodigo(getDistribuidoraCodigo())
						.fechaActualizacion(new Date())
						.tipo(getTipoDistribuidora())
						.cantidadDeProductosAlmacenados(size)
						.build()));

		this.productoEspecificoServicio.actualizarDatos(productos);
	}

	/**
	 * Inicializacion encargada de verificar Base de datos.<br>
	 * Verifica si existe la implementacion en la base de datos, en caso de no
	 * existir, crea una con los datos de la misma.<br>
	 * Toda implementacion debe tener seteado:
	 * {@link BuscadorDeProductos#distribuidoraCodigo},{@link BuscadorDeProductos#tipoDistribuidora}
	 * para poder realizar esta verificacion.
	 */
	private void verificarExistenciaEnBaseDeDatosEspecifica() {
		if (!this.datoDistribuidoraServicio
				.existsByDistribuidora(getDistribuidoraCodigo())) {
			System.out.println(
					this.distribuidoraCodigo + " no existe, creando ...");

			this.datoDistribuidoraServicio.actualizarDatos(
					Collections.singletonList(DatosDistribuidora.builder()
							.distribuidoraCodigo(getDistribuidoraCodigo())
							.tipo(getTipoDistribuidora())
							.cantidadDeProductosAlmacenados(0)
							.fechaActualizacion(new Date()).build()));
		}
	}

}
