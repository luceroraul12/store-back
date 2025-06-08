package distribuidora.scrapping.services;

import distribuidora.scrapping.entities.Config;

public interface ConfigService {
	Config getByCode(String code);
}
