package distribuidora.scrapping.entities;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateRequestWeb extends UpdateRequest {

	@Builder
	public UpdateRequestWeb(String distribuidoraCodigo) {
		super(distribuidoraCodigo);
	}
}
