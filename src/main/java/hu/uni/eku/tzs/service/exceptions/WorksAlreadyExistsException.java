package hu.uni.eku.tzs.service.exceptions;

public class WorksAlreadyExistsException extends Exception{
    public WorksAlreadyExistsException() {
    }

    public WorksAlreadyExistsException(String message) {
        super(message);
    }

    public WorksAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public WorksAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public WorksAlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
