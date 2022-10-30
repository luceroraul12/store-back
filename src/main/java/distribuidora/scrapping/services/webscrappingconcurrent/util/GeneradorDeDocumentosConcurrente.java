package distribuidora.scrapping.services.webscrappingconcurrent.util;

import distribuidora.scrapping.entities.ProductoEspecifico;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Getter
@Setter
public class GeneradorDeDocumentosConcurrente implements Callable<List<Document>> {

    private String url;
    private Integer indiceInicial;
    private Integer indiceFinal;

    public GeneradorDeDocumentosConcurrente(String url, Integer indiceInicial, Integer indiceFinal) {
        this.url = url;
        this.indiceInicial = indiceInicial;
        this.indiceFinal = indiceFinal;
    }

    @Override
    public List<Document> call() throws Exception {
        List<Document> documentosPorHilo = new ArrayList<>();
        for (int i = indiceInicial; i <= indiceFinal; i++) {
            try {
                System.out.println(url+i);
                documentosPorHilo.add(Jsoup.connect(url+i).get());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return documentosPorHilo;
    }
}
