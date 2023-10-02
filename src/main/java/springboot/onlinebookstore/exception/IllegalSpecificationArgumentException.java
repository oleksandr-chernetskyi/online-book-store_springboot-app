package springboot.onlinebookstore.exception;

public class IllegalSpecificationArgumentException extends RuntimeException {
    public IllegalSpecificationArgumentException(String message) {
        super(message);
    }
}
