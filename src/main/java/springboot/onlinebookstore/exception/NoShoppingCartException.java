package springboot.onlinebookstore.exception;

public class NoShoppingCartException extends RuntimeException {
    public NoShoppingCartException(String message) {
        super(message);
    }
}
