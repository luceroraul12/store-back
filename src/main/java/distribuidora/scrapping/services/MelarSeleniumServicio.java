package distribuidora.scrapping.services;

import distribuidora.scrapping.entities.MelarEntidad;
import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.entities.UnionEntidad;
import distribuidora.scrapping.enums.Distribuidora;
import distribuidora.scrapping.util.MelarUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class MelarSeleniumServicio extends ScrapperTablaAbstract<MelarEntidad> {

    @Autowired
    WebDriver driver;

    @Autowired
    MelarUtil melarUtil;

    public MelarSeleniumServicio() {
        setUrlBuscador("https://listadepreciosmelar.com.ar");
        setDistribuidora(Distribuidora.MELAR);
    }

    @Override
    protected Document generarDocument() throws IOException {
        driver.get(getUrlBuscador());
        String template = driver.getPageSource();

        return Jsoup.parse(template);
    }

    @Override
    protected Elements generarElementosProductos(Document doc) {
        return doc.getElementsByTag("table")
                .select("table > tbody > tr:not(.group)");
    }

    @Override
    protected void trabajarProductos(Elements productos) {

        List<String> renglon = new ArrayList<>();


        productos.forEach(p -> {
            Elements partes = p.getElementsByTag("td");
            renglon.clear();
            partes.forEach(td -> {
                renglon.add(td.text());
            });
            double precioFraccionado;
            double precioGranel;

            try{
                precioFraccionado = Double.parseDouble(renglon.get(6)
                        .replaceAll("\\.","")
                        .replaceAll(",","."));
            } catch (Exception e){
                precioFraccionado = 0.0;
            }

            try{
                precioGranel = Double.parseDouble(renglon.get(7)
                        .replaceAll("\\.","")
                        .replaceAll(",","."));
            } catch (Exception e){
                precioGranel = 0.0;
            }



            agregarProducto(MelarEntidad.builder()
                    .codigo(renglon.get(0))
                    .producto(renglon.get(1))
                    .fraccion(renglon.get(2))
                    .granel(renglon.get(3))
                    .origen(renglon.get(4))
                    .medida(renglon.get(5))
                    .precioFraccionado(precioFraccionado)
                    .precioGranel(precioGranel)
                    .build());
        });

        setContadorPaginasVacias(10);

    }

    @Override
    protected Collection<Producto> convertirProductos(UnionEntidad<MelarEntidad> dataDB) {
        return melarUtil.arregloToProducto(dataDB.getDatos());
    }
}
