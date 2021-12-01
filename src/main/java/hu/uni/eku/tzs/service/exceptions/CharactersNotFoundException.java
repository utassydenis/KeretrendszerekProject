package hu.uni.eku.tzs.service.exceptions;

public class CharactersNotFoundException extends Exception{

    public CharactersNotFoundException() {
    }

    public CharactersNotFoundException(String message) {
        super(message);
    }

    public CharactersNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CharactersNotFoundException(Throwable cause) {
        super(cause);
    }

    public CharactersNotFoundException(
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
