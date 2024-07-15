package vn.unigap.api.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.unigap.api.model.ResponseWrapper;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler({NotFoundException.class, BadRequestException.class})
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

  @ExceptionHandler({ExpiredJwtException.class, JwtException.class})
  public ResponseEntity<ResponseWrapper<?>> handleErrorToken(JwtException ex) {
    logger.warn("Handling JwtException: {}", ex.getMessage());

    // Create a response wrapper with appropriate error details
    ResponseWrapper<?> apiExceptionResponse = new ResponseWrapper<>();
    apiExceptionResponse.setErrorCode(-1);
    apiExceptionResponse.setMessage("Token Error: " + ex.getMessage());
    apiExceptionResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());

    // Return ResponseEntity with 401 status code
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiExceptionResponse);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public final ResponseEntity<ResponseWrapper<?>> handleAccessDeniedException(
      AccessDeniedException ex) {
    logger.warn("Access denied: {}", ex.getMessage());
    ResponseWrapper<?> apiExceptionResponse = new ResponseWrapper<>();
    apiExceptionResponse.setErrorCode(-1);
    apiExceptionResponse.setMessage("Access denied: " + ex.getMessage());
    apiExceptionResponse.setStatusCode(HttpStatus.FORBIDDEN.value());

    return new ResponseEntity<>(apiExceptionResponse, HttpStatus.FORBIDDEN);
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
