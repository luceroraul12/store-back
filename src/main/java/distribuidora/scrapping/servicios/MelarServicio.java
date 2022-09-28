package distribuidora.scrapping.servicios;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pruebas.simples.entidad.MelarEntidad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Como de momento no tengo otro Canal de busqueda similar, opto por dejarlo como una clase ya implementada y no que extiende de un modelo
 */
public class MelarServicio {

    private String url = "https://listadepreciosmelar.com.ar";
    private List<MelarEntidad> arreglosConvertidos;





    public List<MelarEntidad> getProductos() throws IOException {
        generarProductos();
        return this.arreglosConvertidos;
    }




    private void generarProductos() throws IOException {

        List<String> producto = new ArrayList<>();
        List<List<String>> arregloProductos = new ArrayList<>();
        arreglosConvertidos = new ArrayList<>();

        Document doc = Jsoup.connect("https://listadepreciosmelar.com.ar").get();

        Elements soloScripts = doc.getElementsByTag("script");

        for (Element script: soloScripts){
            //obtengo el script que contiene la data
            if (script.toString().contains("var data =")){

                //divido por los finales de linea con ;
                String[] divididoPorPuntoComa = script.toString().split(";");

                Arrays.stream(divididoPorPuntoComa).toList().forEach(linea -> {
                    //identifico linea que tiene la variable data
                    if (linea.contains("var data")){
                        //obtengo arreglo en forma de string
                        System.out.println(linea);
                        Pattern p = Pattern.compile("\\[(.*?)\\]");
                        Matcher m = p.matcher(linea);
                        //trabajando una objeto
                        while (m.find()){
                            producto.clear();
                            System.out.println(m.group(1));
                            Pattern patLinea = Pattern.compile("\\\"(.+?)\\\"|(\\d+\\.?\\d+)");
                            Matcher matLinea = patLinea.matcher(m.group(1));
                            //busco los elementos del objeto

                            while (matLinea.find()){
                                //solo tomo los valores no nulos, debido a que hay muchisimos nulos, no tengo idea del por que
                                String resultado = "";

                                if (matLinea.group(1) != null){
                                    System.out.println(matLinea.group(1));
                                    resultado = matLinea.group(1);
                                } else {
                                    System.out.println(matLinea.group(2));
                                    resultado = matLinea.group(2);
                                }
                                ;
                                producto.add(resultado.replaceAll("\\ +$",""));

                            }
                            arregloProductos.add(producto.stream().toList());
                        }

                    }
                });


                arregloProductos.forEach(p -> {
                    arreglosConvertidos.add(convertirStringToMelar(p));
                });

                arreglosConvertidos.forEach(System.out::println);

            }
        }

    }

    private static MelarEntidad convertirStringToMelar(List<String> producto){
        int tamaño = producto.size();
        double precioFraccionado = 0.0;
        double precioGranel = 0.0;
        String nombre = producto.get(2);
        try {
            precioFraccionado = Double.parseDouble(producto.get(tamaño-2));
        } catch (Exception e) {
            precioFraccionado = 0.0;
        }
        try {
            precioGranel = Double.parseDouble(producto.get(tamaño-1));
        } catch (Exception e){
            precioGranel = 0.0;
        }



        return MelarEntidad
                .builder()
                .producto(nombre)
                .precioFraccionado(precioFraccionado)
                .precioGranel(precioGranel)
                .build();
    }

}
