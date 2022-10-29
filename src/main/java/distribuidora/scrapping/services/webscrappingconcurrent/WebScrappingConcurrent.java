package distribuidora.scrapping.services.webscrappingconcurrent;

import distribuidora.scrapping.entities.ProductoEspecifico;
import distribuidora.scrapping.services.webscrapping.BusquedorPorWebScrapping;
import distribuidora.scrapping.services.webscrappingconcurrent.generadordedocumentos.GeneradorDeDocumentosConcurrente;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Es una clase muy similar
 * @param <Entidad>
 */
public abstract class WebScrappingConcurrent<Entidad extends ProductoEspecifico> extends BusquedorPorWebScrapping<Entidad> {

    @Autowired
    GeneradorDeDocumentosConcurrente<Entidad> generadorDeDocumentosConcurrente;

    @Override
    protected List<Document> generarDocumentos() throws IOException {
        int hilosMaximos = 4;
        int maximoIndicePaginador = generarUltimoIndicePaginador();
        int rangoPorHilo = maximoIndicePaginador / hilosMaximos;

        ExecutorService hilos = Executors.newFixedThreadPool(4);

        List<Document> documentosFinales = new ArrayList<>();
        Object cerrojo = new Object();

//        TODO: esto deberia ser concurrente
        for (int i = 0; i < hilosMaximos; i++) {
            int indiceInicial = i*rangoPorHilo;
            int indiceFinal = (i+1)*rangoPorHilo;
            GeneradorDeDocumentosConcurrente<Entidad> hilo = generadorDeDocumentosConcurrente.getInstancia();
            hilo.setearValoresIniciales(
                    getUrlBuscador(),
                    indiceInicial,
                    indiceFinal,
                    documentosFinales,
                    cerrojo
            );
            System.out.println("esto es un bean: "+ hilo);
            hilos.execute(hilo);
        }

        hilos.shutdown();
        while (!hilos.isShutdown()){}

        return documentosFinales;
    }
    protected abstract int generarUltimoIndicePaginador() throws IOException;
}
