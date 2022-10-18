package distribuidora.scrapping.entities;

import distribuidora.scrapping.enums.Distribuidora;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class PeticionFrontEndDocumento {
    private Distribuidora distribuidora;
    private MultipartFile[] excels;
}
