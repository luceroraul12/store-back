package distribuidora.scrapping.services.webscrappingconcurrent.generadordedocumentos;

import distribuidora.scrapping.entities.productos.especificos.LaGranjaDelCentroEntidad;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class GeneradorDeDocumentosConcurrentLaGranjaDelCentro extends GeneradorDeDocumentosConcurrente<LaGranjaDelCentroEntidad>{
}
