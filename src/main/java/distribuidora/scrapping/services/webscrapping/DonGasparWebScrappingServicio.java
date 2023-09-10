package distribuidora.scrapping.services.webscrapping;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.productos.especificos.DonGasparEntidad;
import distribuidora.scrapping.util.DonGasparUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DonGasparWebScrappingServicio extends BusquedorPorWebScrapping<DonGasparEntidad> {
    @Autowired
    DonGasparUtil donGasparUtil;

    @Override
    protected boolean esDocumentValido(Document document) throws Exception {
        return false;
    }

    @Override
    protected DonGasparEntidad obtenerProductosAPartirDeElements(Element elementProducto) {
        new ArrayList<>();
        String id = elementProducto.attr("id").toString();
        double precio;
        try {
            precio = Double.parseDouble(
                    elementProducto.select(".precio-box").text()
                            .replace("$",""));
        } catch(Exception e) {
            precio = 0;
        }
        String descripcion = elementProducto.select(".dfloat-left").text();

        return DonGasparEntidad.builder()
                .id(id)
                .distribuidoraCodigo(getDistribuidoraCodigo())
                .nombreProducto(descripcion)
                .precio(precio)
                .build();
    }

    @Override
    protected Elements filtrarElementos(Document documento) {
        return documento.getElementsByClass("producto");
    }


    @Override
    protected void initImplementacion() {
        setDistribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_DON_GASPAR);
        setUrlBuscador("https://pidorapido.com/dongasparsj");
    }
}
