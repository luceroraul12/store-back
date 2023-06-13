package distribuidora.scrapping.services.general;

import distribuidora.scrapping.entities.LookupValor;
import distribuidora.scrapping.repositories.postgres.LookupValorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LookupServiceImpl implements LookupService{

	@Autowired
	private LookupValorRepository repository;

	@Override
	public List<LookupValor> getLookupValoresPorLookupTipoCodigo(String lookupTipoCodigo) {
		return repository.getLookupValoresPorLookupTipoCodigo(lookupTipoCodigo);
	}

    @Override
    public LookupValor getlookupValorPorCodigo(String categoriaCodigo) {
        return repository.getlookupValorPorCodigo(categoriaCodigo);
    }
}
