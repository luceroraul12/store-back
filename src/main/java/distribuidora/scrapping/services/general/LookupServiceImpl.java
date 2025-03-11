package distribuidora.scrapping.services.general;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.dto.LookupValueDto;
import distribuidora.scrapping.entities.LookupValor;
import distribuidora.scrapping.repositories.postgres.LookupValorRepository;
import distribuidora.scrapping.util.converters.LookupValueDtoConverter;

@Service
public class LookupServiceImpl implements LookupService {

	@Autowired
	private LookupValorRepository repository;

	@Autowired
	private LookupValueDtoConverter converter;

	@Override
	public List<LookupValor> getLookupValoresPorLookupTipoCodigo(String lookupTipoCodigo) {
		return repository.getLookupValoresPorLookupTipoCodigo(lookupTipoCodigo);
	}

	@Override
	public LookupValor getLookupValueByCode(String categoriaCodigo) {
		return repository.getlookupValorPorCodigo(categoriaCodigo);
	}

	@Override
	public List<LookupValueDto> getLookupValueDtoListByLookupTypeCode(String code) {
		return converter.toDtoList(getLookupValoresPorLookupTipoCodigo(code));
	}

	@Override
	public List<LookupValor> getLookupValuesByIds(List<Integer> lvUnitIds) {
		return repository.findAllById(lvUnitIds);
	}

}
