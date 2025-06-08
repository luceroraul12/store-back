package distribuidora.scrapping.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.entities.Config;
import distribuidora.scrapping.repositories.ConfigRepository;

@Service
public class ConfigServiceImpl implements ConfigService {

	@Autowired
	ConfigRepository repo;

	@Override
	public Config getByCode(String code) {
		return repo.findByCode(code);
	}

}
