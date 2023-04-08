package distribuidora.scrapping.services.webscrapping;

import distribuidora.scrapping.comunicadores.Comunicador;
import distribuidora.scrapping.entities.PeticionWebScrapping;
import distribuidora.scrapping.entities.ProductoEspecifico;
import distribuidora.scrapping.enums.Distribuidora;
import distribuidora.scrapping.enums.TipoDistribuidora;
import distribuidora.scrapping.services.BuscadorDeProductos;
import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase base para los servicios basados en Web Scrapping.
 * En caso de que alguna pagina no carge el template desde el programa pero si en el navegador, se debera utilizar un WebDriver que simule el uso del navegador.
 * @param <Entidad>
 */
@Data
public abstract class BusquedorPorWebScrapping<Entidad extends ProductoEspecifico> extends BuscadorDeProductos<Entidad, PeticionWebScrapping> {

    @Autowired
    private Comunicador comunicador;

    /**
     * Obligatorio para poder comenzar a utilizar el servicio
     */
    private String urlBuscador;
    /**
     * Variable booleana utilizada para indicar si los productos estan distribuidos por paginadores.
     * En caso de true: se utilizara un metodo especifico para la generacion de las nuevas URL.
     * Valor por defecto: false.
     * @see BusquedorPorWebScrapping#generarNuevaURL(int)
     */
    private Boolean esBuscadorConPaginador = false;

    /**
     * Variable booleana utilizada para indicar si es necesario contar con un Webdriver para generar los templates de la pagina Web.
     */
    private Boolean esNecesarioUsarWebDriver = false;

    @Autowired
    WebDriver driver;



    @Override
    public List<Entidad> adquirirProductosEntidad(PeticionWebScrapping peticionWebScrapping) {
        List<Entidad> productostotales = new ArrayList<>();
        try {
            productostotales = generarDocumentos()
                    .stream()
                    .parallel()
                    .map(this::obtenerProductosPorDocument)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println("Problemas al generar productos");;
        }
        return productostotales;
    }

    /**
     * Encargado de crear todas los documentos asociados a la URL de la pagina.
     * Tener en cuenta si usa paginacion.
     * @return lista de documentos asociados a la pagina.
     * @throws IOException
     * @see BusquedorPorWebScrapping#generarNuevaURL(int)
     */
    protected List<Document> generarDocumentos() throws IOException {
        List<Document> documentos = new ArrayList<>();
        if (esBuscadorConPaginador){
            int contador = 1;
            Document doc = Jsoup.connect(generarNuevaURL(contador)).get();
            try {
                while(esDocumentValido(doc)){
                    documentos.add(doc);
                    contador++;
                    doc = generarDocumento(generarNuevaURL(contador));
                }
            } catch (Exception e) {
                System.out.println("problemas al validar Url: " + contador);;
            }
        } else {
            documentos.add(
                    generarDocumento(urlBuscador)
            );
        }
        return documentos;
    }

    /**
     * Encargado de generar un documento.
     * Tener en cuenta la variable esNecesarioWebDriver para esta generacion, debido a que el ordenador debera tener instalado los driver scomo el navegador seleccionado.
     * @return documento
     */
    private Document generarDocumento(String url) {
        Document documentoGenerado;
        System.out.println(url);
        if (esNecesarioUsarWebDriver){
            driver.get(url);
            String template = driver.getPageSource();
            documentoGenerado = Jsoup.parse(template);
            driver.close();
        } else {
            try {
                documentoGenerado = Jsoup.connect(url).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return documentoGenerado;
    }

    /**
     * Genera una nueva URL.
     * Toma la URL original y una variable contador para poder generar la nueva URL.
     * @param contador externo, va en incremento 1
     * @return nueva URL
     */
    private String generarNuevaURL(int contador) {
        return urlBuscador+contador ;
    }

    /**
     * Encargado de validar las nuevas paginas creadas.
     * Es un metodo que deben aplicar las clases Especificas, debido a que cada pagina puede ser diferente del resto.
     * Solo es utilizado cuando se trabaja con paginador
     * @param document template de la pagina Web
     * @return true en caso de que sea valido
     * @see BusquedorPorWebScrapping#esBuscadorConPaginador
     * @see BusquedorPorWebScrapping#generarNuevaURL(int contador)
     */
    protected abstract boolean esDocumentValido(Document document) throws Exception;

    /**
     * Encargado de extraer productos Especificos de cada Document.
     * Extrae todos los datos posibles del mismo para poder crear productos especificos.
     * @param documento uno de los tanttos document que puede traer una pagina Web.
     * @return listado de productos especificos.
     */
    private List<Entidad> obtenerProductosPorDocument(Document documento){
        List<Entidad> productosPorDocumento = obtenerTodosLosProductosDeLaPagina(filtrarElementos(documento));
        return productosPorDocumento;
    }

    private List<Entidad> obtenerTodosLosProductosDeLaPagina(Elements elements){
        return elements
                .stream()
                        .map(this::obtenerProductosAPartirDeElements)
                                .collect(Collectors.toList());
    }

    /**
     * Genera un producto especifico a partir de un element.
     * @param elementProducto elemento que contiene datos
     * @return un producto especifico
     */
    protected abstract Entidad obtenerProductosAPartirDeElements(Element elementProducto);

    /**
     * Deja solo los elementos que contienen datos convertibles a productos.
     * @param documento documento que contiene elementos
     * @return elementos filtrados
     */
    protected abstract Elements filtrarElementos(Document documento);

    @Override
    protected void initTipoBusqueda() {
        setTipoDistribuidora(TipoDistribuidora.WEB_SCRAPPING);
        comunicador.getDisparadorActualizacion()
                .filter(peticion -> peticion.getClass() == PeticionWebScrapping.class)
                .cast(PeticionWebScrapping.class)
                .subscribe(
                        peticionWebScrapping -> {
                    if (peticionWebScrapping.getDistribuidora() == this.getDistribuidora()){
                        System.out.println("actualiza "+ getDistribuidora());
                        this.generarProductosEntidadYActualizarCollecciones(peticionWebScrapping);
                    } else if (peticionWebScrapping.getDistribuidora() == Distribuidora.TODAS) {
                        System.out.println("actualizacion General");
                        this.generarProductosEntidadYActualizarCollecciones(peticionWebScrapping);
                    } else {
                        System.out.println("no actualiza "+ getDistribuidora());
                    }
                        },
                        error -> System.out.println("error en "+ getDistribuidora()));
    }

    @Override
    public void destroy(){
        System.out.println("finalizando comunicador: "+this.getClass());
        comunicador.getDisparadorActualizacion().onComplete();
    }

}
