package distribuidora.scrapping.util;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.entities.productos.especificos.FacundoEntidad;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FacundoUtil extends ProductoExcelUtil<FacundoEntidad> {

    private String categoriaExcel;

    @Override
    public List<Producto> convertirProductoyDevolverlo(FacundoEntidad productoEntidad) {
        String distribuidoraCodigo = Constantes.LV_DISTRIBUIDORA_FACUNDO;
        List<Producto> productosGenerados = new ArrayList<>();
        String descripcionMayor = String.format(
                "%s - %s",
                productoEntidad.getCategoriaRenglon(),
                productoEntidad.getCategoria()
        );

        productosGenerados.add(
                Producto.builder()
                        .descripcion(descripcionMayor)
                        .precioPorCantidadEspecifica(productoEntidad.getPrecioMayor() != null
                            ? productoEntidad.getPrecioMayor()
                            : 0.0)
                        .distribuidoraCodigo(distribuidoraCodigo)
                        .build()
        );

        return productosGenerados;
    }
    @Override
    public FacundoEntidad convertirRowEnProductoEspecifico(Row row, String distribuidoraCodigo) {

//        if(esRenglonCategoria(row)){
//            this.categoriaExcel = row.getCell(0).getStringCellValue();
//            return null;
//        }

        Double precioMayor = validarPrecio(row.getCell(2));
        Double precioMenor = validarPrecio(row.getCell(3));
        return FacundoEntidad.builder()
                .distribuidoraCodigo(distribuidoraCodigo)
                .categoria(row.getCell(0).getStringCellValue())
                .categoriaRenglon(this.categoriaExcel)
                .subcategoria(row.getCell(1).toString())
//                .cantidad(row.getCell(2).getStringCellValue())
                .precioMayor(precioMayor)
                .precioMenor(precioMenor)
                .build();
    }

//    private boolean esRenglonCategoria(Row row) {
//        boolean resultado = false;
//        try{
//            if (row.getCell(0).getCellType().equals(CellType.STRING)){
//                if (row.getCell(1).getStringCellValue().length() <= 50){
//                    if (row.getCell(2).getCellType().equals(CellType.STRING)){
//                        if (row.getCell(3).getCellType().equals(CellType.STRING)){
//                            if (row.getCell(4).getCellType().equals(CellType.STRING)){
//                                resultado = true;
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (Exception ignored){
//        }
//        return resultado;
//    }

    private Double validarPrecio(Cell cell) {
        try {
            return cell.getNumericCellValue();
        } catch (Exception e) {
            return 0.0;
        }
    }


}
