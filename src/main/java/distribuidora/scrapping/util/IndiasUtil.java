package distribuidora.scrapping.util;

import distribuidora.scrapping.entities.Producto;
import distribuidora.scrapping.entities.productos.especificos.IndiasEntidad;
import distribuidora.scrapping.enums.Distribuidora;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class IndiasUtil extends ProductoExcelUtil<IndiasEntidad>{
    @Override
    public List<Producto> convertirProductoyDevolverlo(IndiasEntidad productoEntidad) {
        Distribuidora distribuidora = Distribuidora.INDIAS;
        String descripcion = String.format(
                "%s",
                productoEntidad.getDescripcion()
        );
        return Collections.singletonList(Producto.builder()
                .descripcion(descripcion)
                .precioPorCantidadEspecifica(productoEntidad.getPrecio())
                .distribuidora(distribuidora)
                .build());
    }

    @Override
    public IndiasEntidad convertirRowEnProductoEspecifico(Row row, Distribuidora distribuidora) {
        return IndiasEntidad.builder()
                .distribuidora(distribuidora)
                .rubro(row.getCell(1).toString())
                .codigo((int) row.getCell(2).getNumericCellValue())
                .descripcion(row.getCell(3).toString())
                .precio(row.getCell(4).getNumericCellValue())
                .build();
    }
}
