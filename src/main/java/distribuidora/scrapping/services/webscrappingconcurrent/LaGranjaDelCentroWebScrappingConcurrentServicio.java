package distribuidora.scrapping.services.webscrappingconcurrent;

import distribuidora.scrapping.entities.productos.especificos.LaGranjaDelCentroEntidad;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

//@Service
public class LaGranjaDelCentroWebScrappingConcurrentServicio extends WebScrappingConcurrent<LaGranjaDelCentroEntidad>{
    @Override
    protected void initImplementacion() {
        setUrlBuscador("https://lagranjadelcentro.com.ar/productos.php?pagina=");
    }

    @Override
    protected boolean esDocumentValido(Document document) throws Exception {
        return false;
    }

    @Override
    protected LaGranjaDelCentroEntidad obtenerProductosAPartirDeElements(Element elementProducto) {
        return null;
    }

    @Override
    protected Elements filtrarElementos(Document documento) {
        return null;
    }

    @Override
    protected int generarUltimoIndicePaginador() throws IOException {
        Document document = Jsoup.connect(getUrlBuscador()).get();
        Elements element = document.select(".paginador > .p");
        String url = element.get(1).attr("href");
        return Integer.parseInt(url.split("=")[1]);
    }
}
