package distribuidora.scrapping.services.webscrapping;

import distribuidora.scrapping.entities.productos.especificos.DonGasparEntidad;
import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.enums.Distribuidora;
import distribuidora.scrapping.util.DonGasparUtil;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class DonGasparWebScrappingServicio extends BusquedorPorWebScrapping<DonGasparEntidad> {
    @Autowired
    DonGasparUtil donGasparUtil;

    @Override
    protected boolean esDocumentValido(Document document) {
        return false;
    }

    @Override
    protected List<DonGasparEntidad> obtenerProductosAPartirDeElements(Elements elements) {
        List<DonGasparEntidad> productosGenerados = new ArrayList<>();

        elements.forEach( p -> {
            double precio;
            try {
                precio = Double.parseDouble(
                        p.select(".precio-box").text()
                                .replace("$",""));
            } catch(Exception e) {
                precio = 0;
            }
            String descripcion = p.select(".dfloat-left").text();

            productosGenerados.add(
                    DonGasparEntidad.builder()
                            .distribuidora(getDistribuidora())
                            .nombreProducto(descripcion)
                            .precio(precio)
                            .build()
            );
        });

        return productosGenerados;
    }

    @Override
    protected Elements filtrarElementos(Document documento) {
        return documento.getElementsByClass("producto_fila");
    }


    @Override
    protected void initImplementacion() {
        setDistribuidora(Distribuidora.DON_GASPAR);
        setUrlBuscador("https://pidorapido.com/dongasparsj");
        setEsNecesarioUsarWebDriver(true);
    }
}
