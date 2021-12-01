package hu.uni.eku.tzs.service.exceptions;

public class ParagraphsAlreadyExistsException extends Exception{
    public ParagraphsAlreadyExistsException() {
    }

    public ParagraphsAlreadyExistsException(String message) {
        super(message);
    }

    public ParagraphsAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParagraphsAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public ParagraphsAlreadyExistsException(
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
