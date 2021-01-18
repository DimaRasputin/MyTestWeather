package net.extracode.selenium.ezwim.exception;

public class EzwimException extends RuntimeException {

    public EzwimException(String message) {
        super(message);
    }

    public EzwimException(String message, Throwable cause) {
        super(message, cause);
    }
}
