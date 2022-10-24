package distribuidora.scrapping.services.webscrapping;

import distribuidora.scrapping.entities.productos.especificos.SudamerikEntidad;
import distribuidora.scrapping.entities.productos.especificos.SudamerikEntidad.SudamerikConjuntoEspecifico;
import distribuidora.scrapping.enums.Distribuidora;
import distribuidora.scrapping.util.SudamerikUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SudamerikWebScrappingServicio extends BusquedorPorWebScrapping<SudamerikEntidad> {

    @Autowired
    SudamerikUtil sudamerikUtil;

    /**
     * En este momento, tiene una paginacion maxima de 48
     * Esta pagina al superar el limite de paginacion no contiene redireccionamiento, por lo que sigue buscando y al no tener productos muestra una ventana sin productos pero muestra el sistema de paginaciones.
     * Esta paginacion muestra todos los indices sin importar en que paginacion se encuentre. por lo tanto, intentare relacionarlo al ultimo indice que en teoria siempre sera la ultima pagina del paginador. Esto es posible por que el sistema de paginacion, coloca en negrita la paginacion actual y cuando se excede del rango, no existe un indice de pagina en negrita.
     * @param document template de la pagina Web
     * @return
     */
    @Override
    protected boolean  esDocumentValido(Document document) {
        boolean esDocumentoValido = false;
        document.location();
        Elements paginador = document.select("ul.pagination > li > a > b");
        if (!paginador.text().equals("")) esDocumentoValido = true;
        return esDocumentoValido;
    }

    @Override
    protected SudamerikEntidad obtenerProductosAPartirDeElements(Element elementProducto) {
        String nombreProducto = elementProducto.getElementsByClass("nombre").text();
        Elements conjuntoPreciosElements = elementProducto.getElementsByClass("number");
        return SudamerikEntidad.builder()
                .distribuidora(getDistribuidora())
                .nombreProducto(nombreProducto)
                .cantidadesEspecificas(
                        obtenerTodosLosConjuntosDePrecios(conjuntoPreciosElements)
                )
                .build();
    }

    @Override
    protected Elements filtrarElementos(Document documento) {
        return documento.select("div.productos-container");
    }

    /**
     * Toma el conjunto de precios en elements y lo convierte a Objeto.<br>
     * Itera cada conjunto, tiene en cuenta la cantidadEspecifica y el precio.
     * @param conjuntoPreciosElements Previamente filtrados
     * @return lista de conjunto de precios para productos de Sudamerik
     * @see SudamerikConjuntoEspecifico
     */
    private List<SudamerikConjuntoEspecifico> obtenerTodosLosConjuntosDePrecios(Elements conjuntoPreciosElements) {
        List<SudamerikConjuntoEspecifico> conjuntoDePrecios = new ArrayList<>();
        conjuntoPreciosElements.forEach(
                conjunto -> {
                    conjuntoDePrecios.add(
                            SudamerikConjuntoEspecifico.builder()
                                    .cantidadEspecifica(
                                            conjunto
                                                    .getElementsByClass("unidad-tipo")
                                                    .text()
                                    )
                                    .precio(Double.valueOf(
                                            conjunto
                                                    .getElementsByClass("precio")
                                                    .text()
                                                    .replaceAll("\\$", "")))
                                    .build()
                    );
                }
        );
        return conjuntoDePrecios;
    }

    @Override
    protected void initImplementacion() {
        setDistribuidora(Distribuidora.SUDAMERIK);
        setEsBuscadorConPaginador(true);
        setUrlBuscador("https://www.sudamerikargentina.com.ar/productos/pagina/");
    }
}
