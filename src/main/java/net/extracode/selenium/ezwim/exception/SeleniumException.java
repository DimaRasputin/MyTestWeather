package net.extracode.selenium.ezwim.exception;

public class SeleniumException extends RuntimeException {

    public SeleniumException(String message) {
        super(message);
    }

    public SeleniumException(String message, Throwable cause) {
        super(message, cause);
    }

}
