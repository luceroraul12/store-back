package distribuidora.scrapping.services;

import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.entities.UnionEntidad;
import distribuidora.scrapping.enums.Distribuidora;
import distribuidora.scrapping.repositories.UnionRepository;
import distribuidora.scrapping.util.MelarUtil;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public abstract class ScrapperTablaAbstract<Entidad> {

    @Autowired
    private UnionRepository<Entidad> unionRepository;

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

    protected abstract void trabajarProductos(Elements productos);

    public String generarSiguienteUrl(){
        return this.urlBuscador + this.contador;
    }


    /*
    primero se debe revisar la base de datos, si la fecha no es valida, se tiene que volver a scrapear la data. en caso contrario, se devuelvo lo almacenado
     */
    public Collection<Producto> getProductosRecolectados() throws IOException {
        UnionEntidad<Entidad> dataDB = unionRepository.findByDistribuidora(this.distribuidora);
        Collection<Producto> productosConvertidos = new ArrayList<>();
        
        if(esValidoRecolectarDeNuevo(dataDB)){
            System.out.println("recargo info");
            dataDB = recolectarProductos();
        } else {
            System.out.println("Es muy temprano, envio la info existente");
        }

        productosConvertidos = convertirProductos(dataDB);

        return productosConvertidos;
    }

    protected abstract Collection<Producto> convertirProductos(UnionEntidad<Entidad> dataDB);

    private boolean esValidoRecolectarDeNuevo(UnionEntidad<Entidad> dataDB) {
        boolean resultado = false;
        try{
            long diferenciaDeFecha = ChronoUnit
                    .DAYS
                    .between(dataDB.getFechaScrap(),LocalDate.now());
            boolean noEsAntesDeTiempo = diferenciaDeFecha >= intervaloDeRenovacionDeDatos;
            resultado = noEsAntesDeTiempo;
        } catch (Exception e){
            resultado = true;
        }
        return resultado;
    }
    private UnionEntidad<Entidad> recolectarProductos() throws IOException {
        reiniciar();
        while (this.contadorPaginasVacias <= 10){
            Document document = generarDocument();
            System.out.println(document);
            Elements productos = generarElementosProductos(document);
            if (productos.size() == 0){
                contadorPaginasVacias++;
            }
            contador++;
            trabajarProductos(productos);
        }
        unionRepository.deleteUnionEntidadByDistribuidora(this.distribuidora);

        UnionEntidad<Entidad> union = new UnionEntidad();
        union.setDatos(this.productosRecolectados);
        union.setFechaScrap(LocalDate.now());
        union.setDistribuidora(this.distribuidora);

        unionRepository.save(union);

        return union;
    }

    protected Elements generarElementosProductos(Document doc){
        return doc.getElementsByClass(clasesTabla);
    }

    protected Document generarDocument() throws IOException {
        return Jsoup.connect(generarSiguienteUrl()).get();
    }

    private void reiniciar(){
        this.contador = 0;
        this.contadorPaginasVacias = 0;
        this.productosRecolectados = new ArrayList<>();
    }

    protected void agregarProducto(Entidad producto){
        this.productosRecolectados.add(producto);
    }


}
