package distribuidora.scrapping.services.webscrapping;

import distribuidora.scrapping.entities.productos.especificos.LaGranjaDelCentroEntidad;
import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.enums.Distribuidora;
import distribuidora.scrapping.util.LaGranjaDelCentroUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class LaGranjaDelCentroWebScrappingServicio extends BusquedorPorWebScrapping<LaGranjaDelCentroEntidad> {

    @Autowired
    LaGranjaDelCentroUtil laGranjaDelCentroUtil;

    /**
     * Para este caso, contienen un apartado  de paginador.
     * En el template aparece la siguiente etiqueta 'span' con los siguientes atributos:<br>
     * span class="p-activo">123 <br>
     * Esta etiqueta solo esta presente cuando es una pagina valida. Por lo tanto voy a condicionar en funcion a eso.
     * @param document template de la pagina Web siguiente
     * @return
     */
    @Override
    protected boolean esDocumentValido(Document document) {
        boolean esValido = false;
         for (Element element : document.getElementsByTag("span")){
            if (element.hasClass("p-activo")){
                esValido = true;
            };
        }

        return esValido;
    }

    @Override
    protected List<LaGranjaDelCentroEntidad> obtenerProductosAPartirDeElements(Elements elements) {
        List<LaGranjaDelCentroEntidad> productosFinales = new ArrayList<>();
        elements.forEach(
                element -> {
                    productosFinales.add(
                            LaGranjaDelCentroEntidad.builder()
                                    .distribuidora(getDistribuidora())
                                    .nombreProducto(
                                            element.getElementsByClass("h3-content-1").text()
                                    )
                                    .precio(
                                            Double.valueOf(element
                                                    .getElementsByClass("p-precio-content-1")
                                                    .text()
                                                    .replaceAll("[$.]","")
                                                    .replaceAll(",","."))
                                    )
                                    .build()
                    );
                }
        );
        return productosFinales;
    }

    @Override
    protected Elements filtrarElementos(Document documento) {
        return documento
                .select("div.box-content-1");
    }

    @Override
    protected void initImplementacion() {
        setDistribuidora(Distribuidora.LA_GRANJA_DEL_CENTRO);
        setEsBuscadorConPaginador(true);
        setUrlBuscador("https://lagranjadelcentro.com.ar/productos.php?pagina=");
    }
}
