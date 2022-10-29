package distribuidora.scrapping.services.webscrappingconcurrent.generadordedocumentos;

import distribuidora.scrapping.entities.ProductoEspecifico;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class GeneradorDeDocumentosConcurrente<Entidad extends ProductoEspecifico> implements Runnable{

    private String url;
    private Integer indiceInicial;
    private Integer indiceFinal;

    private static Object serrojo;

    private static List<Document> documentosTotales = new ArrayList<>();
    private List<Document> documentosPorHilo = new ArrayList<>();

    @Override
    public void run()  {
        for (int i = indiceInicial; i <= indiceFinal; i++) {
            try {
                System.out.println(url+i);
                documentosPorHilo.add(Jsoup.connect(url+i).get());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        synchronized (serrojo){
            documentosTotales.addAll(documentosPorHilo);
        }
    }

    @Bean
    @Scope("prototype")
    public GeneradorDeDocumentosConcurrente<Entidad> getInstancia(){
        return new GeneradorDeDocumentosConcurrente<Entidad>() {};
    }

    /**
     * metodo necesario por instancia
     */
    public void setearValoresIniciales(String url, Integer indiceInicial, Integer indiceFinal, List<Document> documetosFinales, Object cerrojo){
        this.url = url;
        this.indiceInicial = indiceInicial;
        this.indiceFinal = indiceFinal;
        documentosTotales = documetosFinales;
        serrojo = cerrojo;
    };
}
