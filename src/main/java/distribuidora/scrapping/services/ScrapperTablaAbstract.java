package distribuidora.scrapping.services;

import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class ScrapperTablaAbstract<Entidad> {

    private String clasesTabla;
    private String clasesNombreProducto;
    private String clasesPrecio;
    private String urlBuscador;
    private Integer contador;
    private Integer contadorPaginasVacias;
    private List<Entidad> productosRecolectados;
    private LocalTime momentoDeRecoleccion;
    //deben ser en minutos
    private int intervaloDeRenovacionDeDatos = 30;



    private void recolectarProductos() throws IOException {
        reiniciar();
        while (this.contadorPaginasVacias <= 2){
            Document document = generarDocument();
            Elements productos = generarElementosProductos(document);
            if (productos.size() == 0){
                contadorPaginasVacias++;
            }
            contador++;
            trabajarProductos(productos);


        }

    }

    protected Elements generarElementosProductos(Document doc){
        return doc.getElementsByClass(clasesTabla);
    }

    protected Document generarDocument() throws IOException {
        return Jsoup.connect(generarSiguienteUrl()).get();
    }

    protected abstract void trabajarProductos(Elements productos);


    private void reiniciar(){
        this.contador = 0;
        this.contadorPaginasVacias = 0;
        this.productosRecolectados = new ArrayList<>();
    }

    protected void agregarProducto(Entidad producto){
        this.productosRecolectados.add(producto);
    }

    public String generarSiguienteUrl(){
        return this.urlBuscador + this.contador;
    }


    public List<Entidad> getProductosRecolectados() throws IOException {

        if(esValidoRecolectarDeNuevo()){
            this.momentoDeRecoleccion = LocalTime.now();
            System.out.println("recargo info");
            System.out.println(this.momentoDeRecoleccion);
            recolectarProductos();
        } else {
            System.out.println("Es muy temprano, envio la info existente");
        }
        return this.productosRecolectados;
    }

    private boolean esValidoRecolectarDeNuevo() {
        boolean resultado = false;

        try{

            System.out.println(this.momentoDeRecoleccion);

            System.out.println(LocalTime.now());


            boolean noEsAntesDeTiempo = ChronoUnit
                    .MINUTES
                    .between(this.momentoDeRecoleccion,LocalTime.now()) > intervaloDeRenovacionDeDatos;

            resultado = noEsAntesDeTiempo;

        } catch (Exception e){
            e.printStackTrace();
            resultado = true;
        }
        System.out.println("volver a buscar?");
        System.out.println(resultado);
        return resultado;
    }

}
