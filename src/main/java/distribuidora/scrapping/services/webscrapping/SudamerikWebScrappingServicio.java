//package distribuidora.scrapping.services.webscrapping;
//
//import distribuidora.scrapping.entities.Producto;
//import distribuidora.scrapping.entities.productos.especificos.SudamerikEntidad;
//import distribuidora.scrapping.entities.UnionEntidad;
//import distribuidora.scrapping.enums.Distribuidora;
//import distribuidora.scrapping.util.SudamerikUtil;
//import org.jsoup.select.Elements;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Collection;
//import java.util.List;
//
//@Service
//public class SudamerikWebScrappingServicio extends BusquedorPorWebScrapping<SudamerikEntidad> {
//
//    @Autowired
//    SudamerikUtil sudamerikUtil;
//    private final String claseConjunto = "number";
//    private final String claseTipo = "unidad-tipo";
//
//    public SudamerikWebScrappingServicio() {
//        setUrlBuscador("https://www.sudamerikargentina.com.ar/productos/pagina/");
//        setClasesPrecio("precio");
//        setClasesNombreProducto("nombre");
//        setClasesTabla("productos-container");
//        setDistribuidora(Distribuidora.SUDAMERIK);
//    }
//
//
//    @Override
//    protected void trabajarConElementsyObtenerProductosEspecificos(Elements productos) {
//        productos.forEach(
//                p -> {
//                    Elements productoEnConjuntos = p.getElementsByClass(claseConjunto);
//                    productoEnConjuntos.forEach(
//                            pConjunto -> {
//                                agregarProducto(
//                                    SudamerikEntidad
//                                            .builder()
//                                            .nombreProducto(
//                                                    p.getElementsByClass(getClasesNombreProducto()).text()
//                                            )
//                                            .cantidadEspecifca(
//                                                    pConjunto.getElementsByClass(claseTipo).text()
//                                            )
//                                            .precio(
//                                                    Double.valueOf
//                                                            (pConjunto.
//                                                                    getElementsByClass(getClasesPrecio())
//                                                                    .text()
//                                                                    .replaceAll("\\$",""))
//                                            )
//                                            .build()
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
//
//    @Override
//    protected List<Producto> mapearEntidadaProducto(SudamerikEntidad productoEntidad) {
//        return null;
//    }
//}
