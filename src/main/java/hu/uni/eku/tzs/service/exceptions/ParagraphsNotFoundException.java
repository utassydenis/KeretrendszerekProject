package hu.uni.eku.tzs.service.exceptions;

public class ParagraphsNotFoundException extends Exception {
    public ParagraphsNotFoundException() {
    }

    public ParagraphsNotFoundException(String message) {
        super(message);
    }

    public ParagraphsNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParagraphsNotFoundException(Throwable cause) {
        super(cause);
    }

    public ParagraphsNotFoundException(
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
