package Projects.DoItNow_BackEnd_App.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import Projects.DoItNow_BackEnd_App.payloads.ErrorsPayload;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExceptionsHandler {

	// * * * * * * * * * * BadRequestException 400 * * * * * * * * * *
	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorsPayload handleBadRequest(BadRequestException e) {
		return new ErrorsPayload(e.getMessage(), new Date());
	}

	// * * * * * * * * * * UnauthorizedException 401 * * * * * * * * * *
	@ExceptionHandler(UnauthorizedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorsPayload handleUnauthorized(UnauthorizedException e) {
		return new ErrorsPayload(e.getMessage(), new Date());
	}

	// * * * * * * * * * * NotFoundException 404 * * * * * * * * * *
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorsPayload handleNotFound(NotFoundException e) {
		return new ErrorsPayload(e.getMessage(), new Date());
	}
}
