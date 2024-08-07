package distribuidora.scrapping.security.handler;

import org.springframework.web.bind.annotation.ResponseBody;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ResponseBody
public class ErrorResponse {
	private final String message;

	@Override
	public String toString() {
		return String.format("{\"message\":\"%s\"}", message);
	}	
}
