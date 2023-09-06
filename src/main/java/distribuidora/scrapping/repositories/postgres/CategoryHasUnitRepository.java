package distribuidora.scrapping.repositories.postgres;

import distribuidora.scrapping.entities.CategoryHasUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryHasUnitRepository extends JpaRepository<CategoryHasUnit, Integer> {

    @Query("SELECT chu FROM CategoryHasUnit chu ")
    List<CategoryHasUnit> findAll();
}
