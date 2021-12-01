package hu.uni.eku.tzs.service.exceptions;

public class CharactersAlreadyExistsException extends Exception{
    public CharactersAlreadyExistsException() {
    }

    public CharactersAlreadyExistsException(String message) {
        super(message);
    }

    public CharactersAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CharactersAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public CharactersAlreadyExistsException(
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
