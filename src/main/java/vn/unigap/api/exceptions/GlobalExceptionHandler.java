package vn.unigap.api.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.unigap.api.model.ResponseWrapper;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NotFoundException.class,BadRequestException.class})
    public final ResponseWrapper<?> handleException(Exception ex) {
        ResponseWrapper<?> apiExceptionResponse = new ResponseWrapper<>();
        apiExceptionResponse.setErrorCode(-1);
        apiExceptionResponse.setMessage(ex.getMessage());
        if (ex instanceof NotFoundException) {
            apiExceptionResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        } else if (ex instanceof BadRequestException) {
            apiExceptionResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        }

        return apiExceptionResponse;
    }
}