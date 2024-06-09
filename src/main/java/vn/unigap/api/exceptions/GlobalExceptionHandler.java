package vn.unigap.api.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.unigap.api.model.ResponseWrapper;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({NotFoundException.class,BadRequestException.class})
    public final ResponseWrapper<?> handleException(Exception ex) {
        logger.debug("Exception occurred: {}", ex.getMessage());
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

    @ExceptionHandler(Exception.class)
    public final ResponseWrapper<?> handleServerException(Exception ex) {
        logger.error("Internal server error occurred: {}", ex.getMessage(), ex);
        ResponseWrapper<?> apiExceptionResponse = new ResponseWrapper<>();
        apiExceptionResponse.setErrorCode(-1);
        apiExceptionResponse.setMessage("Something went wrong on the server " + ex.getMessage());
        apiExceptionResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return apiExceptionResponse;
    }
}