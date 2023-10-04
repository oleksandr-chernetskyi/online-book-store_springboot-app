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
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(STATUS, HttpStatus.BAD_REQUEST);

        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(this::getErrorsMessage)
                .toList();
        body.put(ERRORS, errors);
        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundException(
            EntityNotFoundException ex,
            HttpHeaders headers,
            HttpStatus status
    ) {
        return handleException(ex, headers, status);
    }

    @ExceptionHandler(KeyNotSupportException.class)
    protected ResponseEntity<Object> handleKeyNotSupportedException(
            KeyNotSupportException ex,
            HttpHeaders headers,
            HttpStatus status
    ) {
        return handleException(ex, headers, status);
    }

    @ExceptionHandler(IllegalSpecificationArgumentException.class)
    protected ResponseEntity<Object> handleIllegalSpecificationArgumentException(
            IllegalSpecificationArgumentException ex,
            HttpHeaders headers,
            HttpStatus status
    ) {
        return handleException(ex, headers, status);
    }

    @ExceptionHandler
    private ResponseEntity<Object> handleException(
            RuntimeException ex,
            HttpHeaders headers,
            HttpStatus status
    ) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(STATUS, HttpStatus.BAD_REQUEST);

        String error = ex.getMessage();
        body.put(ERRORS, error);

        return new ResponseEntity<>(body, headers, status);
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
