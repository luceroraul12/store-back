package distribuidora.scrapping.services;

import distribuidora.scrapping.entities.SudamerikEntidad;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class SudamerikServicio extends ScrapperTablaAbstract<SudamerikEntidad>{

    private final String claseConjunto = "number";
    private final String claseTipo = "unidad-tipo";

    public SudamerikServicio() {
        setUrlBuscador("https://www.sudamerikargentina.com.ar/productos/pagina/");
        setClasesPrecio("precio");
        setClasesNombreProducto("nombre");
        setClasesTabla("productos-container");
    }


    @Override
    protected void trabajarProductos(Elements productos) {
        productos.forEach(
                p -> {
                    Elements productoEnConjuntos = p.getElementsByClass(claseConjunto);
                    productoEnConjuntos.forEach(
                            pConjunto -> {
                                agregarProducto(
                                    SudamerikEntidad
                                            .builder()
                                            .nombreProducto(
                                                    p.getElementsByClass(getClasesNombreProducto()).text()
                                            )
                                            .cantidadEspecifca(
                                                    pConjunto.getElementsByClass(claseTipo).text()
                                            )
                                            .precio(
                                                    Double.valueOf
                                                            (pConjunto.
                                                                    getElementsByClass(getClasesPrecio())
                                                                    .text()
                                                                    .replaceAll("\\$",""))
                                            )
                                            .build()
                                );
                                System.out.println(
                                        SudamerikEntidad
                                                .builder()
                                                .nombreProducto(
                                                        p.getElementsByClass(getClasesNombreProducto()).text()
                                                )
                                                .cantidadEspecifca(
                                                        pConjunto.getElementsByClass(claseTipo).text()
                                                )
                                                .precio(
                                                        Double.valueOf
                                                                (pConjunto.
                                                                        getElementsByClass(getClasesPrecio())
                                                                        .text()
                                                                        .replaceAll("\\$",""))
                                                )
                                                .build()
                                );
                            }
                    );
                }
        );

    }
}
