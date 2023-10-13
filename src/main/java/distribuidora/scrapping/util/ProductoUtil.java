package distribuidora.scrapping.util;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import distribuidora.scrapping.entities.Producto;

public abstract class ProductoUtil<Entidad> {

	/**
	 * Toma un conjunto de Producto Especifico y lo convierte a conjunto de
	 * Producto
	 * 
	 * @param productos
	 * @return
	 */
	public List<Producto> arregloToProducto(List<Entidad> productos) {
		int numeroDeHilos = 4;
		int cantidadDeProductosEntidad = productos.size();
		int intervalo = cantidadDeProductosEntidad / numeroDeHilos;
		ExecutorService hilos = Executors.newFixedThreadPool(numeroDeHilos);

		List<List<Entidad>> listaDivididaEnPartas = new ArrayList<>();
		List<Future<List<Producto>>> futureList = new ArrayList<>();

		for (int i = 0; i < numeroDeHilos; i++) {
			int indiceInicial = i * intervalo;
			int indiceFinal = i == numeroDeHilos - 1
					? cantidadDeProductosEntidad
					: (i + 1) * intervalo;
			listaDivididaEnPartas
					.add(productos.subList(indiceInicial, indiceFinal));
		}
		for (List<Entidad> lista : listaDivididaEnPartas) {
			futureList.add(hilos.submit(new Callable<List<Producto>>() {
				@Override
				public List<Producto> call() throws Exception {
					return lista.stream().map(
							productoEntidad -> convertirProductoyDevolverlo(
									productoEntidad))
							.flatMap(Collection::stream)
							.peek(p -> p.setDescripcion(
									quitarAcentosYDevolverMayuscula(
											p.getDescripcion())))
							.collect(Collectors.toList());
				}
			}));
		}
		hilos.shutdown();
		return futureList.stream().map(listFuture -> {
			try {
				return listFuture.get();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} catch (ExecutionException e) {
				throw new RuntimeException(e);
			}
		}).filter(Objects::nonNull).flatMap(Collection::stream)
				.collect(Collectors.toList());
	}

	/**
	 * ingresa un producto Especifico y salen todas las conversiones necesarias.
	 * En caso de querer convertir propiedades en descripcion utilizar el metodo
	 * validador.
	 * 
	 * @param productoSinConvertir
	 * @return puede que sea uno solo o sean un conjunto, depende de la Entidad
	 *         Especifica
	 * @see ProductoUtil#validaryAgregarPropiedadesQueNoEstanRepetidasEnOriginal(String,
	 *      List)
	 */
	public abstract List<Producto> convertirProductoyDevolverlo(
			Entidad productoSinConvertir);

	private String quitarAcentosYDevolverMayuscula(String s) {
		s = Normalizer.normalize(s, Normalizer.Form.NFD);
		s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		return s.toUpperCase();
	}

}
