package distribuidora.scrapping.entities;

import distribuidora.scrapping.enums.Distribuidora;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * Clase padre de cualquier producto Entidad diferente de Producto.
 * Tiene la finalidad de unificar todas los productos de Entidades Especificas y que se trabaje en funcion a esta.
 * Cada clase especifica a cada distribuidora debe heredar de esta clase.
 */

public abstract class ProductoEspecifico {
    @Id
    private String id;
    private Distribuidora distribuidora;

    public ProductoEspecifico(Distribuidora distribuidora) {
        this.distribuidora = distribuidora;
    }

    public Distribuidora getDistribuidora() {
        return this.distribuidora;
    }

    public void setDistribuidora(Distribuidora distribuidora) {
        this.distribuidora = distribuidora;
    }
}
