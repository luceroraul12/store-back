package distribuidora.scrapping.servicios;

import distribuidora.scrapping.entidad.LaGranjaDelCentroEntidad;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class LaGranjaDelCentroServicio extends ScrapperTablaAbstract{

    public LaGranjaDelCentroServicio() {
        setUrlBuscador("https://lagranjadelcentro.com.ar/productos.php?pagina=");
        setClasesTabla("box-content-1");
        setClasesNombreProducto("h3-content-1");
        setClasesPrecio("p-precio-content-1");
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
}
