package distribuidora.scrapping.entities;

import distribuidora.scrapping.enums.Distribuidora;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Es el tipo de dato que se obtiene del FrontEnd cuando se quiere hacer una actualizacion por medio de Documento
 * @see distribuidora.scrapping.controller.ActualizacionController
 */
@Data
@Builder
public class PeticionFrontEndDocumento {
    private Distribuidora distribuidora;
    private MultipartFile[] excels;
}
