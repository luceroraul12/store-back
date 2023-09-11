package distribuidora.scrapping.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import distribuidora.scrapping.entities.Producto;

public interface ProductoRepository extends MongoRepository<Producto, String> {

    void deleteAllByDistribuidoraCodigo(String distribuidoraCodigo);

	Producto findByDistribuidoraCodigoAndId(String distribuidoraCodigo,
			String id);
}
