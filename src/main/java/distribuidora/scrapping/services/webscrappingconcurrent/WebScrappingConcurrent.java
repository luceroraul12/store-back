package distribuidora.scrapping.services.webscrappingconcurrent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;

import distribuidora.scrapping.entities.ProductoEspecifico;
import distribuidora.scrapping.services.webscrapping.BusquedorPorWebScrapping;
import distribuidora.scrapping.services.webscrappingconcurrent.util.GeneradorDeDocumentosConcurrente;

/**
 * Clase heredada de {@link BusquedorPorWebScrapping} que implementa concurrencia.
 * De esta forma, se reducen los tiempos de espera cuando se trabje con ella.
 * @param <Entidad>
 */
public abstract class WebScrappingConcurrent<Entidad extends ProductoEspecifico> extends BusquedorPorWebScrapping<Entidad> {

    /**
     * Metodo que usa concurrencia para generar los documentos.
     * @return
     * @throws IOException
     */
    @Override
    protected List<Document> generarDocumentos() throws IOException {
        int hilosMaximos = 4;
        int maximoIndicePaginador = generarUltimoIndicePaginador();
        int rangoPorHilo = maximoIndicePaginador / hilosMaximos;

        ExecutorService hilos = Executors.newFixedThreadPool(4);
        List<Future<List<Document>>> resultadosParciales = new ArrayList<>();

        for (int i = 0; i < hilosMaximos; i++) {
            int indiceInicial = i == 0 ? 1 : i*rangoPorHilo;
            int indiceFinal = i == (hilosMaximos-1) ? maximoIndicePaginador : (i+1)*rangoPorHilo - 1;
            resultadosParciales.add(hilos.submit(
                    new GeneradorDeDocumentosConcurrente(
                            getUrlBuscador(),
                            indiceInicial,
                            indiceFinal
                    )
            ));
        }
        hilos.shutdown();

        List<Document> documentosFinales = resultadosParciales
//                ingreso lista de futures
                .stream()
//                convierto listas de futures a lista de documents
                .map(listFuture -> {
                    try {
                        return listFuture.get();
                    } catch ( Exception e) {
                        System.out.println("problemas en futures list documents");
                        return null;
                    }
                })
                .filter(Objects::nonNull)
//                descompongo list de documents para tenerlos en forma de documents
                .flatMap(Collection::stream)
//                genero una unica lista con todos los documents
                .collect(Collectors.toList());

        return documentosFinales;
    }

    /**
     * Meotodo que obtiene el indice maximo del paginador.
     * @return indice maximo de paginador.
     * @throws IOException
     */
    protected abstract int generarUltimoIndicePaginador() throws IOException;
}
