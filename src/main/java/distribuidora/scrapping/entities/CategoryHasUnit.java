package distribuidora.scrapping.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "lv_category_has_lv_unit")
public class CategoryHasUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "lv_category_id")
    private LookupValor category;
    @ManyToOne
    @JoinColumn(name = "lv_unit_id")
    private LookupValor unit;
}
