package distribuidora.scrapping.services;

import distribuidora.scrapping.entities.MelarEntidad;
import distribuidora.scrapping.entities.UnionEntidad;
import distribuidora.scrapping.enums.Distribuidora;
import distribuidora.scrapping.repositories.UnionRepository;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class ScrapperTablaAbstract<Entidad> {

    @Autowired
    UnionRepository<Entidad> unionRepository;

    private Distribuidora distribuidora;

    private String clasesTabla;
    private String clasesNombreProducto;
    private String clasesPrecio;
    private String urlBuscador;
    private Integer contador;
    private Integer contadorPaginasVacias;
    private List<Entidad> productosRecolectados;
    //deben ser en Dias
    private int intervaloDeRenovacionDeDatos = 1;



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
        unionRepository.deleteAll();

        UnionEntidad<Entidad> union = new UnionEntidad();
        union.setDatos(this.productosRecolectados);
        union.setFechaScrap(LocalDate.now());
        union.setDistribuidora(this.distribuidora);

        unionRepository.save(union);

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
            System.out.println("recargo info");
            recolectarProductos();
        } else {
            System.out.println("Es muy temprano, envio la info existente");
        }
        return unionRepository.obtenerProductos(this.distribuidora).getDatos();
    }

    private boolean esValidoRecolectarDeNuevo() {
        boolean resultado = false;

        try{
            UnionEntidad<Entidad> dataAlmacenada = unionRepository.obtenerProductos(this.distribuidora);
            boolean noEsAntesDeTiempo = ChronoUnit
                    .DAYS
                    .between(dataAlmacenada.getFechaScrap(),LocalDate.now()) > intervaloDeRenovacionDeDatos;

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
