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
        boolean esValido = false;
        for (Element element : document.getElementsByTag("span")){
            if (element.hasClass("p-activo")){
                esValido = true;
            };
        }

        return esValido;
    }

    @Override
    protected LaGranjaDelCentroEntidad obtenerProductosAPartirDeElements(Element elementProducto) {
        return LaGranjaDelCentroEntidad.builder()
                .distribuidora(getDistribuidora())
                .nombreProducto(
                        elementProducto.getElementsByClass("h3-content-1").text()
                )
                .precio(
                        Double.valueOf(elementProducto
                                .getElementsByClass("p-precio-content-1")
                                .text()
                                .replaceAll("[$.]","")
                                .replaceAll(",","."))
                )
                .build();
    }

    @Override
    protected Elements filtrarElementos(Document documento) {
        return documento
                .select("div.box-content-1");
    }

    @Override
    protected int generarUltimoIndicePaginador() throws IOException {
        Document document = Jsoup.connect(getUrlBuscador()).get();
        Elements element = document.select(".paginador > .p");
        String url = element.get(1).attr("href");
        return Integer.parseInt(url.split("=")[1]);
    }
}
