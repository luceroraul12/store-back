package distribuidora.scrapping.util;

import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.entities.productos.especificos.VillaresEntidad;
import distribuidora.scrapping.enums.Distribuidora;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class VillaresUtil extends ProductoExcelUtil<VillaresEntidad>{

    @Override
    public VillaresEntidad convertirRowEnProductoEspecifico(Row row, Distribuidora distribuidora) {
        Integer cantidadCeldas = row.getPhysicalNumberOfCells();
        Integer celdasGranel = 12;
        Integer celdasGourmetFraccionado = 11;
        Integer celdasDistribuido = 10;
        Integer indiceInicioCeldaPrecio = 0;
//        inicializo
        String descripcion = "";
        String cantidad = "";
        String cantidadMinima = "";
        String detalle = "";
        String marca = "";
        String unidad = "";
        Double precioLista = 0.0;
        Double precioPrimero = 0.0;
        Double precioSegundo = 0.0;
        Double precioTercero = 0.0;

//        producto granel
        if (cantidadCeldas.equals(celdasGranel)){
            indiceInicioCeldaPrecio = celdasGranel - 4;
            descripcion = row.getCell(2).toString();
            cantidad = row.getCell(3).toString();
            cantidadMinima = row.getCell(4).toString();
            detalle = row.getCell(5).toString();
            marca = row.getCell(6).toString();
            unidad = row.getCell(7).toString();
        }
//        producto gourmet o fraccionado
        if (cantidadCeldas.equals(celdasGourmetFraccionado)){
            indiceInicioCeldaPrecio = celdasGourmetFraccionado - 4;
            descripcion = row.getCell(2).toString();
            cantidad = row.getCell(3).toString();
            detalle = row.getCell(4).toString();
            marca = row.getCell(5).toString();
            unidad = row.getCell(6).toString();
        }
//        producto distribuidos
        if (cantidadCeldas.equals(celdasDistribuido)){
            indiceInicioCeldaPrecio = celdasDistribuido - 4;
            descripcion = row.getCell(2).toString();
            cantidad = row.getCell(3).toString();
            unidad = row.getCell(4).toString();
            marca = row.getCell(5).toString();
        }

//        System.out.println("cantidad de celdas:" + cantidadCeldas + " - " + "indice celda precio:" + indiceInicioCeldaPrecio);
        Boolean estaDisponible = row.getCell(indiceInicioCeldaPrecio).getCellType().equals(CellType.NUMERIC);
        if (estaDisponible){
            System.out.println(row.getCell(indiceInicioCeldaPrecio).toString());
            precioLista = row.getCell(indiceInicioCeldaPrecio).getNumericCellValue();
            try{
                precioPrimero = row.getCell(indiceInicioCeldaPrecio+1).getNumericCellValue();
                precioSegundo = row.getCell(indiceInicioCeldaPrecio+2).getNumericCellValue();
                precioTercero = row.getCell(indiceInicioCeldaPrecio+3).getNumericCellValue();
            } catch (Exception ignored) {
            }
        }

        return new VillaresEntidad(
        		null,
                Distribuidora.VILLARES,
                descripcion,
                cantidad,
                cantidadMinima,
                detalle,
                marca,
                unidad,
                precioLista,
                precioPrimero,
                precioSegundo,
                precioTercero
        );
    }

    @Override
    public List<Producto> convertirProductoyDevolverlo(VillaresEntidad productoSinConvertir) {
        String marca = !productoSinConvertir.getMarca().isEmpty()
                ? productoSinConvertir.getMarca() + ":"
                : "";
        String cantidades = !productoSinConvertir.getCantidadMinima().isEmpty()
                ? productoSinConvertir.getCantidad() + "-" + productoSinConvertir.getCantidadMinima()
                : productoSinConvertir.getCantidad();
        String descripcion = String.format(
                "%s %s - %s [%s] EN: %s",
                marca,
                productoSinConvertir.getDescripcion(),
                productoSinConvertir.getDetalle(),
                cantidades,
                productoSinConvertir.getUnidad()
                );
        return Collections.singletonList(Producto.builder()
                .descripcion(descripcion)
                .precioPorCantidadEspecifica(productoSinConvertir.getPrecioLista())
                .distribuidora(Distribuidora.VILLARES)
                .build());
    }
}
