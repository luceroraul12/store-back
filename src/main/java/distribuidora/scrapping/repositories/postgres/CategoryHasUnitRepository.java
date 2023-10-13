package distribuidora.scrapping.repositories.postgres;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import distribuidora.scrapping.entities.CategoryHasUnit;

public interface CategoryHasUnitRepository extends JpaRepository<CategoryHasUnit, Integer> {

    @Query("SELECT chu FROM CategoryHasUnit chu ")
    List<CategoryHasUnit> findAll();
}
