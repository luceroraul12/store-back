package distribuidora.scrapping.services.webscrapping;

import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.entities.productos.especificos.SudamerikEntidad;
import distribuidora.scrapping.entities.UnionEntidad;
import distribuidora.scrapping.enums.Distribuidora;
import distribuidora.scrapping.util.SudamerikUtil;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class SudamerikWebScrappingServicio extends BusquedorPorWebScrapping<SudamerikEntidad> {

    @Autowired
    SudamerikUtil sudamerikUtil;
//    private final String claseConjunto = "number";
//    private final String claseTipo = "unidad-tipo";

    public SudamerikWebScrappingServicio() {
        urlBuscador = "https://www.sudamerikargentina.com.ar/productos/pagina/";
        distribuidora = Distribuidora.SUDAMERIK;
//        setClasesPrecio("precio");
//        setClasesNombreProducto("nombre");
//        setClasesTabla("productos-container");
    }

    /**
     * En este momento, tiene una paginacion maxima de 48
     * Esta pagina al superar el limite de paginacion no contiene redireccionamiento, por lo que sigue buscando y al no tener productos muestra una ventana sin productos pero muestra el sistema de paginaciones.
     * Esta paginacion muestra todos los indices sin importar en que paginacion se encuentre. por lo tanto, intentare relacionarlo al ultimo indice que en teoria siempre sera la ultima pagina del paginador.
     * @param document template de la pagina Web
     * @return
     */
    @Override
    protected boolean esDocumentValido(Document document) {
        boolean esDocumentoValido = false;
        document.location();
        Elements paginador = document.select("ul.pagination > li > a > b");
        if (!paginador.text().equals("")) esDocumentoValido = true;
        return esDocumentoValido;
    }

    @Override
    protected List<SudamerikEntidad> obtenerProductosAPartirDeElements(Elements elements) {
        return null;
    }

    @Override
    protected Elements filtrarElementos(Document documento) {
        return null;
    }

    @Override
    protected List<Producto> mapearEntidadaProducto(SudamerikEntidad productoEntidad) {
        return null;
    }

//    @Override
//    protected void trabajarConElementsyObtenerProductosEspecificos(Elements productos) {
//        productos.forEach(
//                p -> {
//                    Elements productoEnConjuntos = p.getElementsByClass(claseConjunto);
//                    productoEnConjuntos.forEach(
//                            pConjunto -> {
//                                agregarProducto(
//                                        SudamerikEntidad
//                                                .builder()
//                                                .nombreProducto(
//                                                        p.getElementsByClass(getClasesNombreProducto()).text()
//                                                )
//                                                .cantidadEspecifca(
//                                                        pConjunto.getElementsByClass(claseTipo).text()
//                                                )
//                                                .precio(
//                                                        Double.valueOf
//                                                                (pConjunto.
//                                                                        getElementsByClass(getClasesPrecio())
//                                                                        .text()
//                                                                        .replaceAll("\\$",""))
//                                                )
//                                                .build()
//                                );
//                                System.out.println(
//                                        SudamerikEntidad
//                                                .builder()
//                                                .nombreProducto(
//                                                        p.getElementsByClass(getClasesNombreProducto()).text()
//                                                )
//                                                .cantidadEspecifca(
//                                                        pConjunto.getElementsByClass(claseTipo).text()
//                                                )
//                                                .precio(
//                                                        Double.valueOf
//                                                                (pConjunto.
//                                                                        getElementsByClass(getClasesPrecio())
//                                                                        .text()
//                                                                        .replaceAll("\\$",""))
//                                                )
//                                                .build()
//                                );
//                            }
//                    );
//                }
//        );
//
//    }
//
//    @Override
//    protected Collection<Producto> convertirProductos(UnionEntidad<SudamerikEntidad> dataDB) {
//        return sudamerikUtil.arregloToProducto(dataDB.getDatos());
//    }
}
