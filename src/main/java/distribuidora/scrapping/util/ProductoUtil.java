package distribuidora.scrapping.util;

import distribuidora.scrapping.entities.Producto;

import java.util.ArrayList;
import java.util.List;

public abstract class ProductoUtil<Entidad> {

    /**
     * Toma un conjunto de Producto Especifico y lo convierte a conjunto de Producto
     * @param productos
     * @return
     */
    public List<Producto> arregloToProducto(List<Entidad> productos){
        List<Producto> productosConvertidos = new ArrayList<>();

        productos
                .parallelStream()
                .forEach(p -> {
            productosConvertidos.addAll(
                    convertirProductoyDevolverlo(p)
            );
        });

        return productosConvertidos;
    }

    /**
     * ingresa un producto Especifico y salen todas las conversiones necesarias.
     * En caso de querer convertir propiedades en descripcion utilizar el metodo validador.
     * @param productoSinConvertir
     * @return puede que sea uno solo o sean un conjunto, depende de la Entidad Especifica
     * @see ProductoUtil#validaryAgregarPropiedadesQueNoEstanRepetidasEnOriginal(String, List)
     */
    public abstract List<Producto> convertirProductoyDevolverlo(Entidad productoSinConvertir);


    /**
     * Compara el nombre original del producto contra un conjunto de propiedades y agrega segun corresponda.
     * La idea es que contenga la mayor cantidad de propiedades utiles para el cliente siempre y cuando no se encuentren repetidas. Al ser algo que se puede llegar a repetir con varias Entidades Especificas prefiero colocarlo por herencia.
     * En caso de no tener ningun
     * @param nombreProductoOriginal
     * @param propiedadesParaValidar
     * @return
     */
//    TODO: hay que verificar que las implementaciones podriasn llegar a utilizarlo
    protected String validaryAgregarPropiedadesQueNoEstanRepetidasEnOriginal(
            String nombreProductoOriginal,
            List<String> propiedadesParaValidar){
        StringBuilder nombreProductoFinal = new StringBuilder(nombreProductoOriginal);
        propiedadesParaValidar
                .parallelStream()
                .forEach(
                propiedad -> {
                    if (!nombreProductoOriginal.contains(propiedad)){
                        nombreProductoFinal
                                .append(" ")
                                .append(propiedad);
                    }
                }
        );
        return nombreProductoFinal.toString();
    }


}
