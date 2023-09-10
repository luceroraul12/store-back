package distribuidora.scrapping.services.general;

import distribuidora.scrapping.dto.LookupValueDto;
import distribuidora.scrapping.entities.LookupValor;

import java.util.List;

public interface LookupService {

	List<LookupValor> getLookupValoresPorLookupTipoCodigo(String codigo);

    LookupValor getLookupValueByCode(String code);

	List<LookupValueDto> getLookupValueDtoListByLookupTypeCode(String code);
}
