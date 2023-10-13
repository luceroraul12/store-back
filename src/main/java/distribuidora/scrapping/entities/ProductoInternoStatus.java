package distribuidora.scrapping.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "productos_internos_status")
@Getter
@Setter
public class ProductoInternoStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "producto_interno_id")
    private ProductoInterno productoInterno;

    private Boolean isUnit;

    private Boolean hasStock;
}
