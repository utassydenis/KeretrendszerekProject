package hu.uni.eku.tzs.service.exceptions;

public class WorksNotFoundException extends Exception{
    public WorksNotFoundException() {
    }

    public WorksNotFoundException(String message) {
        super(message);
    }

    public WorksNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public WorksNotFoundException(Throwable cause) {
        super(cause);
    }

    public WorksNotFoundException(String message, Throwable cause, boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
