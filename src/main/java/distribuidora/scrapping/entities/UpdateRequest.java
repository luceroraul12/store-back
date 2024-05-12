package distribuidora.scrapping.entities;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public abstract class UpdateRequest {
    private String distribuidoraCodigo;
    private MultipartFile[] multipartFiles;
}
