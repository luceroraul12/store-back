package distribuidora.scrapping.services.webscrappingconcurrent;

import distribuidora.scrapping.entities.ProductoEspecifico;
import distribuidora.scrapping.services.webscrapping.BusquedorPorWebScrapping;
import distribuidora.scrapping.services.webscrappingconcurrent.generadordedocumentos.GeneradorDeDocumentosConcurrente;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Es una clase muy similar
 * @param <Entidad>
 */
public abstract class WebScrappingConcurrent<Entidad extends ProductoEspecifico> extends BusquedorPorWebScrapping<Entidad> {

//    @Autowired
//    GeneradorDeDocumentosConcurrente<Entidad> generadorDeDocumentosConcurrente;

    @Override
    protected List<Document> generarDocumentos() throws IOException {
        int hilosMaximos = 4;
        int maximoIndicePaginador = generarUltimoIndicePaginador();
        int rangoPorHilo = maximoIndicePaginador / hilosMaximos;

        ExecutorService hilos = Executors.newFixedThreadPool(4);
        List<Future<List<Document>>> resultadosParciales = new ArrayList<>();

        List<Document> documentosFinales = new ArrayList<>();

//        TODO: esto deberia ser concurrente
        for (int i = 0; i < hilosMaximos; i++) {
            int indiceInicial = i == 0 ? 1 : i*rangoPorHilo;
            int indiceFinal = i == (hilosMaximos-1) ? maximoIndicePaginador : (i+1)*rangoPorHilo - 1;
            GeneradorDeDocumentosConcurrente hilo = new GeneradorDeDocumentosConcurrente(
                    getUrlBuscador(),
                    indiceInicial,
                    indiceFinal
            );
            System.out.println("esto es un bean: "+ hilo);
            resultadosParciales.add(hilos.submit(hilo));
        }
        hilos.shutdown();

        resultadosParciales.forEach( resultadoPorHilo -> {
            try {
                documentosFinales.addAll(resultadoPorHilo.get());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return documentosFinales;
    }
    protected abstract int generarUltimoIndicePaginador() throws IOException;
}
