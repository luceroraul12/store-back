package distribuidora.scrapping.services.general;

import distribuidora.scrapping.entities.LookupValor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LookupServiceImpl implements LookupService{
	@Override
	public List<LookupValor> getLookupValoresPorLookupTipoCodigo(String codigo) {
		return new ArrayList<>();
	}
}
