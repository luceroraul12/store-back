package distribuidora.scrapping.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.Data;

/**
 * Es la entidad con la que llegan al FrontEnd
 */
@Data
@Builder
@Entity
public class ExternalProduct {
    @Id
    private Integer id;
    @Column
    private String title;
    @Column
    private Double price;
    @Column
    private Date date;
    @ManyToOne()
    @JoinColumn()
    private LookupValor distribuidora;
    @Column
    private String code;

    /**
     * Retorna el valor del precio
     * @return en caso de que el valor sea null se retornara 0.0
     */
    public Double getPrecioPorCantidadEspecifica() {
        return price != null ? price : 0.0;
    }
}
