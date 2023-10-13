package distribuidora.scrapping.services.webscrapping;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.productos.especificos.FacundoEntidad;
import distribuidora.scrapping.enums.TipoDistribuidora;

@Service
public class FacundoRenovadoWebScrappingServicio extends BusquedorPorWebScrapping<FacundoEntidad>{
    @Override
    protected void initImplementacion() {
        setDistribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_FACUNDO);
        setUrlBuscador("http://gglobal.net.ar/bernal/?cliente");
    }

    @Override
    protected boolean esDocumentValido(Document document) {
        return false;
    }

    @Override
    protected FacundoEntidad obtenerProductosAPartirDeElements(Element elementProducto) {
        String id = elementProducto.attr("data-idproducto");
        String nombre = elementProducto
                .getElementsByTag("h5")
                .textNodes().get(0).text()
                .replaceAll("_"," ")
                .subSequence(0, elementProducto.getElementsByTag("h5").textNodes().get(0).text().length()-3)
                .toString();

        String categoria = elementProducto
                .parent()
                .parent()
                .parent()
                .parent()
                .getElementsByTag("h2")
                .text();

        Double precio = Double.valueOf(elementProducto
                .getElementsByTag("strong")
                .text()
                .replaceAll(" ","")
                .subSequence(1, elementProducto.getElementsByTag("strong").text().length()-1)
                .toString());

        return FacundoEntidad.builder()
                .id(id)
                .categoria(nombre)
                .precioMayor(precio)
                .categoriaRenglon(categoria)
                .distribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_FACUNDO)
                .cantidad("")
                .build();
    }

    @Override
    protected Elements filtrarElementos(Document documento) {
        return documento.getElementsByClass("item row pointer");
    }

	@Override
	public TipoDistribuidora getTipoDistribuidora() {
		return TipoDistribuidora.WEB_SCRAPPING;
	}
    
    
}
