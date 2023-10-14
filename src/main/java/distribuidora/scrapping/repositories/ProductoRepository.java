package distribuidora.scrapping.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import distribuidora.scrapping.entities.Producto;

public interface ProductoRepository extends MongoRepository<Producto, String> {

    void deleteAllByDistribuidoraCodigo(String distribuidoraCodigo);

	Producto findByDistribuidoraCodigoAndId(String distribuidoraCodigo,
			String id);

	List<Producto> findByDistribuidoraCodigo(String distribuidoraCodigo);
}
