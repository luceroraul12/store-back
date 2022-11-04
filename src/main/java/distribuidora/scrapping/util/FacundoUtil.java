package distribuidora.scrapping.util;

import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.entities.productos.especificos.FacundoEntidad;
import distribuidora.scrapping.enums.Distribuidora;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FacundoUtil extends ProductoExcelUtil<FacundoEntidad> {

    private String categoriaExcel;

    @Override
    public List<Producto> convertirProductoyDevolverlo(FacundoEntidad productoEntidad) {
        Distribuidora distribuidora = Distribuidora.FACUNDO;
        List<Producto> productosGenerados = new ArrayList<>();
        String descripcionMenor = String.format(
                "%s %s %s X %s X menor",
                productoEntidad.getCategoriaRenglon(),
                productoEntidad.getCategoria(),
                productoEntidad.getSubcategoria(),
                productoEntidad.getCantidad()
        );
        String descripcionMayor = String.format(
                "%s %s %s X %s X mayor",
                productoEntidad.getCategoriaRenglon(),
                productoEntidad.getCategoria(),
                productoEntidad.getSubcategoria(),
                productoEntidad.getCantidad()
        );

        productosGenerados.add(
                Producto.builder()
                        .descripcion(descripcionMayor)
                        .precioPorCantidadEspecifica(productoEntidad.getPrecioMayor())
                        .distribuidora(distribuidora)
                        .build()
        );
        productosGenerados.add(
                Producto.builder()
                        .descripcion(descripcionMenor)
                        .precioPorCantidadEspecifica(productoEntidad.getPrecioMenor())
                        .distribuidora(distribuidora)
                        .build()
        );

        return productosGenerados;
    }
    @Override
    public FacundoEntidad convertirRowEnProductoEspecifico(Row row, Distribuidora distribuidora) {

        if(esRenglonCategoria(row)){
            this.categoriaExcel = row.getCell(0).getStringCellValue();
            return null;
        }

        Double precioMayor = validarPrecio(row.getCell(3));
        Double precioMenor = validarPrecio(row.getCell(4));
        return FacundoEntidad.builder()
                .distribuidora(distribuidora)
                .categoria(row.getCell(0).getStringCellValue())
                .categoriaRenglon(this.categoriaExcel)
                .subcategoria(row.getCell(1).toString())
                .cantidad(row.getCell(2).getStringCellValue())
                .precioMayor(precioMayor)
                .precioMenor(precioMenor)
                .build();
    }

    private boolean esRenglonCategoria(Row row) {
        boolean resultado = false;
        try{
            if (row.getCell(0).getCellType().equals(CellType.STRING)){
                if (row.getCell(1).getStringCellValue().length() <= 50){
                    if (row.getCell(2).getCellType().equals(CellType.STRING)){
                        if (row.getCell(3).getCellType().equals(CellType.STRING)){
                            if (row.getCell(4).getCellType().equals(CellType.STRING)){
                                resultado = true;
                            }
                        }
                    }
                }
            }
        } catch (Exception ignored){
        }
        return resultado;
    }

    private Double validarPrecio(Cell cell) {
        try {
            return cell.getNumericCellValue();
        } catch (Exception e) {
            return 0.0;
        }
    }


}
