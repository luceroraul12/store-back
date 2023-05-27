package distribuidora.scrapping.util;

import distribuidora.scrapping.configs.Constantes;
import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.entities.productos.especificos.IndiasEntidad;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class IndiasUtil extends ProductoExcelUtil<IndiasEntidad>{
    @Override
    public List<Producto> convertirProductoyDevolverlo(IndiasEntidad productoEntidad) {
        String descripcion = String.format(
                "%s",
                productoEntidad.getDescripcion()
        );
        return Collections.singletonList(Producto.builder()
                .id(productoEntidad.getCodigo().toString())
                .descripcion(descripcion)
                .precioPorCantidadEspecifica(productoEntidad.getPrecio())
                .distribuidoraCodigo(Constantes.LV_DISTRIBUIDORA_INDIAS)
                .build());
    }

    @Override
    public IndiasEntidad convertirRowEnProductoEspecifico(Row row, String distribuidoraCodigo) {
        double precio = validarPrecio(row.getCell(4));
        return IndiasEntidad.builder()
                .distribuidoraCodigo(distribuidoraCodigo)
                .rubro(row.getCell(1).toString())
                .codigo((int) row.getCell(2).getNumericCellValue())
                .descripcion(row.getCell(3).toString())
                .precio(precio)
                .build();
    }

    private double validarPrecio(Cell cell) {
        try {
            return cell.getNumericCellValue();
        } catch (Exception e) {
            return 0.0;
        }
    }
}
