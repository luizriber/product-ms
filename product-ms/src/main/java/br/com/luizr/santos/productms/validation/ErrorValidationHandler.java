package br.com.luizr.santos.productms.validation;

import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorValidationHandler {
	
	String message = null;
	final String descriptionNotFound = "Nenhuma dado encontrada com o parâmetro informado";
	final String descriptionErrorGeneric = "Ocorreu um erro genérico, entre em contato com o administrador";
	
	@Autowired
	private MessageSource messageSource;

	//Retorna uma lista de erros de "bad request", de acordo com a exception ocorrida
	@ResponseStatus(code = HttpStatus.BAD_REQUEST) // seta o HTTP Code de response, nesse caso 400
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorDto methodArgumentNotValidException(MethodArgumentNotValidException exception) {	  
		message = "";
		exception.getBindingResult().getFieldErrors().forEach(e -> {
	            message += " / " + messageSource.getMessage(e, LocaleContextHolder.getLocale());
	     });
	     return new ErrorDto(HttpStatus.BAD_REQUEST.value(), message.substring(3));
	}
	
	//Retorna a mensagem de erro "not found", quando um recurso não existir com o id de parametro informado
	@ResponseStatus(code = HttpStatus.NOT_FOUND) // seta o HTTP Code de response, nesse caso 404
	@ExceptionHandler(EntityNotFoundException.class)
	public ErrorDto entityNotFoundException(EntityNotFoundException exception) {
		return new ErrorDto(HttpStatus.NOT_FOUND.value(), descriptionNotFound);
	}

	//Retorna a mensagem de erro "not found", quando um recurso não existir com o id de parametro informado
	@ResponseStatus(code = HttpStatus.NOT_FOUND) // seta o HTTP Code de response, nesse caso 404
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ErrorDto emptyResultDataAccessException(EmptyResultDataAccessException exception) {
		return new ErrorDto(HttpStatus.NOT_FOUND.value(), descriptionNotFound);
	}
	
	//Retorna a mensagem de erro generico
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR) // seta o HTTP Code de response, nesse caso 500
	public ErrorDto errorGeneric() {
		return new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), descriptionErrorGeneric);
	}
}