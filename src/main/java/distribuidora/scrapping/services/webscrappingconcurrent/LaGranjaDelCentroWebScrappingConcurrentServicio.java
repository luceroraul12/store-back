package distribuidora.scrapping.services.webscrappingconcurrent;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import distribuidora.scrapping.entities.productos.especificos.LaGranjaDelCentroEntidad;

//@Service
public class LaGranjaDelCentroWebScrappingConcurrentServicio extends WebScrappingConcurrent<LaGranjaDelCentroEntidad>{
    @Override
    protected void initImplementacion() {
        setUrlBuscador("https://lagranjadelcentro.com.ar/productos.php?pagina=");
    }

    @Override
    protected boolean esDocumentValido(Document document) {
        boolean esValido = false;
        for (Element element : document.getElementsByTag("span")){
            if (element.hasClass("p-activo")){
                esValido = true;
            }
        }

        return esValido;
    }

    @Override
    protected LaGranjaDelCentroEntidad obtenerProductosAPartirDeElements(Element elementProducto) {
        return LaGranjaDelCentroEntidad.builder()
                .distribuidoraCodigo(getDistribuidoraCodigo())
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
