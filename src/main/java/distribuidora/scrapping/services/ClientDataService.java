package distribuidora.scrapping.services;

import distribuidora.scrapping.entities.Client;

public interface ClientDataService {
	
	public Client getById(Integer id);
	
	public Client getByCode(String code);
}
