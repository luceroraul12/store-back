package distribuidora.scrapping.services.webscrappingconcurrent.util;

import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Clase utilitaria para la generacion de documentos EXCEL de manera concurrente.
 */
@Getter
@Setter
public class GeneradorDeDocumentosConcurrente implements Callable<List<Document>> {

    private String url;
    private Integer indiceInicial;
    private Integer indiceFinal;

    /**
     * Constructor por el cual se setea los valores obligatorios
     * @param url de la distribuidora
     * @param indiceInicial del bucle
     * @param indiceFinal del bucle
     */
    public GeneradorDeDocumentosConcurrente(String url, Integer indiceInicial, Integer indiceFinal) {
        this.url = url;
        this.indiceInicial = indiceInicial;
        this.indiceFinal = indiceFinal;
    }

    @Override
    public List<Document> call() throws Exception {
        List<Document> documentosPorHilo = new ArrayList<>();
        for (int i = indiceInicial; i <= indiceFinal; i++) {
        	documentosPorHilo.add(Jsoup.connect(url+i).get());
        }
        return documentosPorHilo;
    }
}
