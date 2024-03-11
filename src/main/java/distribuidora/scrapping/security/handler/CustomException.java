package distribuidora.scrapping.security.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomException extends Throwable {
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleSecurityException(Exception ex) {
		ex.printStackTrace();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ex.getMessage());
	}
}
