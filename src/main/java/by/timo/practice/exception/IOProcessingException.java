package by.timo.practice.exception;

public class IOProcessingException extends RuntimeException {
    public IOProcessingException(String message) {
        super(message);
    }

    public IOProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
