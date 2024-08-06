package distribuidora.scrapping.security.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomException extends Throwable {

	private static final long serialVersionUID = 1L;

	@ExceptionHandler(UsernameNotFoundException.class) // Manejador específico												// primero
	public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(
			UsernameNotFoundException ex) {
		ex.printStackTrace();

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new ErrorResponse(ex.getMessage()));
	}

	@ExceptionHandler(Exception.class) // Manejador general después
	public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
		ex.printStackTrace();
		return ResponseEntity.badRequest()
				.body(new ErrorResponse(ex.getMessage()));
	}
}
