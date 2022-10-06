package distribuidora.scrapping.services;

import distribuidora.scrapping.entities.LaGranjaDelCentroEntidad;
import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.entities.UnionEntidad;
import distribuidora.scrapping.enums.Distribuidora;
import distribuidora.scrapping.util.LaGranjaDelCentroUtil;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class LaGranjaDelCentroServicio extends ScrapperTablaAbstract<LaGranjaDelCentroEntidad> {

    @Autowired
    LaGranjaDelCentroUtil laGranjaDelCentroUtil;

    public LaGranjaDelCentroServicio() {
        setUrlBuscador("https://lagranjadelcentro.com.ar/productos.php?pagina=");
        setClasesTabla("box-content-1");
        setClasesNombreProducto("h3-content-1");
        setClasesPrecio("p-precio-content-1");
        setDistribuidora(Distribuidora.LA_GRANJA_DEL_CENTRO);
    }

    @Override
    protected void trabajarProductos(Elements productos) {

        productos.forEach( producto -> {
            String nombre = producto.getElementsByClass("h3-content-1").text();
            String precio = producto.getElementsByClass("p-precio-content-1").text();

            agregarProducto(
                    LaGranjaDelCentroEntidad
                            .builder()
                            .nombreProducto(
                                    producto.getElementsByClass("h3-content-1").text()
                            )
                            .precio(
                                    Double.valueOf(producto
                                            .getElementsByClass("p-precio-content-1")
                                            .text()
                                            .replaceAll("[$.]","")
                                            .replaceAll(",","."))
                            )
                            .build()
            );
            System.out.println(
                    LaGranjaDelCentroEntidad
                            .builder()
                            .nombreProducto(
                                    producto.getElementsByClass("h3-content-1").text()
                            )
                            .precio(
                                    Double.valueOf(producto
                                            .getElementsByClass("p-precio-content-1")
                                            .text()
                                            .replaceAll("[$.]","")
                                            .replaceAll(",","."))
                            )
                            .build()
            );
        });


    }

    @Override
    protected Collection<Producto> convertirProductos(UnionEntidad<LaGranjaDelCentroEntidad> dataDB) {
        return laGranjaDelCentroUtil.arregloToProducto(dataDB.getDatos());
    }
}
