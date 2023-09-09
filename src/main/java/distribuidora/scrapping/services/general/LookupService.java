package distribuidora.scrapping.services.general;

import distribuidora.scrapping.dto.LookupValueDto;
import distribuidora.scrapping.entities.LookupValor;

import java.util.List;

public interface LookupService {

	List<LookupValor> getLookupValoresPorLookupTipoCodigo(String codigo);

    LookupValor getlookupValorPorCodigo(String categoriaCodigo);

	List<LookupValueDto> getLookupValueDtoListByLookupTypeCode(String code);
}
