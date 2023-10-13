package distribuidora.scrapping.repositories.postgres;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import distribuidora.scrapping.entities.LookupValor;

public interface LookupValorRepository extends JpaRepository<LookupValor, Integer> {
    @Query("SELECT lv FROM LookupValor lv " +
            "   INNER JOIN lv.lookupTipo lt " +
            "WHERE lt.codigo = :lookupTipoCodigo ")
    List<LookupValor> getLookupValoresPorLookupTipoCodigo(@Param("lookupTipoCodigo") String lookupTipoCodigo);

    @Query("SELECT lv FROM LookupValor lv WHERE lv.codigo = :codigo")
    LookupValor getlookupValorPorCodigo(@Param("codigo") String codigo);
}
