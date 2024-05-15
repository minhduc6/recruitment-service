package vn.unigap.api.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.unigap.api.model.ResponseWrapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestControllerAdvice
public class ValidationAdvice  {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public final  ResponseWrapper<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

		final List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		final List<String> errorList = fieldErrors.stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.collect(Collectors.toList());

		final String errorsAsString = String.join(", ", errorList);


		log.warn("Validation errors : {} , Parameters : {}", errorList, exception.getBindingResult().getTarget());

        return new ResponseWrapper<>(
				-1, // Assuming 0 for no specific error code
				HttpStatus.BAD_REQUEST.value(),
				"Validation failed: " + errorsAsString,
				null
		);
	}

}
