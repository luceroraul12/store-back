package distribuidora.scrapping.entities;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UpdateRequest {
	private String code;
	private MultipartFile[] multipartFiles;

	public UpdateRequest(String code, MultipartFile[] multipartFiles) {
		super();
		this.code = code;
		this.multipartFiles = multipartFiles;
	}

}
