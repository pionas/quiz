package info.pionas.quiz.api;

import info.pionas.quiz.domain.shared.exception.NotFoundException;
import info.pionas.quiz.domain.user.api.UserFoundException;
import info.pionas.quiz.domain.user.api.UserPasswordNotMatchedException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice(basePackageClasses = {RestErrorHandler.class})
@Slf4j
class RestErrorHandler {

    private static String getFieldErrorMessage(ObjectError objectError) {
        final var paramName = (objectError instanceof FieldError fieldError) ? fieldError.getField() : objectError.getObjectName();
        return paramName.concat(": ").concat(Objects.requireNonNull(objectError.getDefaultMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, WebExchangeBindException.class})
    public ResponseEntity<Map<String, List<String>>> handleHttpMessageNotReadableException(Exception ex) {
        log.error(ex.getMessage(), ex);
        List<String> errors = ((BindingResult) ex).getAllErrors().stream()
                .map(RestErrorHandler::getFieldErrorMessage)
                .toList();
        return ResponseEntity.badRequest()
                .body(getErrorsMap(errors));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, List<String>>> handleAccessDeniedException(AccessDeniedException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(getErrorsMap(List.of(ex.getMessage())));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleAccessDeniedException(NotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(getErrorsMap(List.of(ex.getMessage())));
    }

    @ExceptionHandler({UserFoundException.class, UserPasswordNotMatchedException.class})
    public ResponseEntity<Map<String, List<String>>> handleUserFoundException(RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(getErrorsMap(List.of(ex.getMessage())));
    }

    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    public ResponseEntity<Map<String, List<String>>> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(getErrorsMap(List.of(ex.getMessage())));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, List<String>>> handleAuthenticationException(AuthenticationException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(getErrorsMap(List.of(ex.getMessage())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, List<String>>> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(getErrorsMap(List.of(ex.getMessage())));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Map<String, List<String>>> handleExpiredJwtException(ExpiredJwtException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(getErrorsMap(List.of(ex.getMessage())));
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
}
