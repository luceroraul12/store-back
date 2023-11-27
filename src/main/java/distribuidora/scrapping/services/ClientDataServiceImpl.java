package distribuidora.scrapping.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import distribuidora.scrapping.entities.Client;
import distribuidora.scrapping.repositories.ClientDataRepository;

@Service
public class ClientDataServiceImpl implements ClientDataService {
	
	@Autowired
	ClientDataRepository repository;

	@Override
	public Client getById(Integer id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public Client getByCode(String code) {
		return repository.findByCode(code);
	}

}
