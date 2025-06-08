package distribuidora.scrapping.services.general;

import java.util.List;

import distribuidora.scrapping.dto.LookupValueDto;
import distribuidora.scrapping.entities.LookupValor;

public interface LookupService {

	List<LookupValor> getLookupValoresPorLookupTipoCodigo(String codigo);

	LookupValor getLookupValueByCode(String code);

	List<LookupValueDto> getLookupValueDtoListByLookupTypeCode(String code);

	List<LookupValor> getLookupValuesByIds(List<Integer> lvUnitIds);
}
