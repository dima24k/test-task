package by.timo.practice.exception;

public class EmployeeDuplicateException extends RuntimeException {
    public EmployeeDuplicateException(String message) {
        super(message);
    }
}