package springboot.onlinebookstore.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String TIMESTAMP = "timestamp";
    private static final String STATUS = "status";
    private static final String ERRORS = "errors";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(this::getErrorsMessage)
                .toList();
        return handleExceptionInternal(ex, getBody(HttpStatus.BAD_REQUEST, errors.toString()),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundException(
            EntityNotFoundException ex,
            WebRequest request
    ) {
        return handleExceptionInternal(ex, getBody(HttpStatus.NOT_FOUND, ex.getMessage()),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = KeyNotSupportException.class)
    protected ResponseEntity<Object> handleKeyNotSupportedException(
            KeyNotSupportException ex,
            WebRequest request
    ) {
        return handleExceptionInternal(ex, getBody(HttpStatus.BAD_REQUEST,
                ex.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = IllegalSpecificationArgumentException.class)
    protected ResponseEntity<Object> handleIllegalSpecificationArgumentException(
            IllegalSpecificationArgumentException ex,
            WebRequest request
    ) {
        return handleExceptionInternal(ex, getBody(HttpStatus.NOT_ACCEPTABLE,
                ex.getMessage()), new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
    }

    @ExceptionHandler(value = RegistrationException.class)
    protected ResponseEntity<Object> handleRegistrationException(
            RegistrationException ex,
            WebRequest request
    ) {
        return handleExceptionInternal(ex, getBody(HttpStatus.CONFLICT,
                ex.getMessage()), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = AuthenticationException.class)
    protected ResponseEntity<Object> handleAuthenticationException(
            AuthenticationException ex,
            WebRequest request
    ) {
        return handleExceptionInternal(ex, getBody(HttpStatus.UNAUTHORIZED,
                ex.getMessage()), new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(value = NoShoppingCartException.class)
    protected ResponseEntity<Object> handleNoShoppingCartException(
            NoShoppingCartException ex,
            WebRequest request
    ) {
        return handleExceptionInternal(ex, getBody(HttpStatus.NOT_FOUND, ex.getMessage()),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = BookNotFoundException.class)
    protected ResponseEntity<Object> handleBookNotFoundException(
            NoShoppingCartException ex,
            WebRequest request
    ) {
        return handleExceptionInternal(ex, getBody(HttpStatus.NOT_FOUND, ex.getMessage()),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    private Map<String, Object> getBody(HttpStatus status, String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(STATUS, status);
        body.put(ERRORS, message);
        return body;
    }

    private String getErrorsMessage(ObjectError e) {
        if (e instanceof FieldError) {
            String field = ((FieldError) e).getField();
            String message = e.getDefaultMessage();
            return field + " " + message;
        }
        return e.getDefaultMessage();
    }
}
