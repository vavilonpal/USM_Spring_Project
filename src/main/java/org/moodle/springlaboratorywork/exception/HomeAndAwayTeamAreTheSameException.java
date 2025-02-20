package org.moodle.springlaboratorywork.exception;

public class HomeAndAwayTeamAreTheSameException extends RuntimeException {
    public HomeAndAwayTeamAreTheSameException() {
        super();
    }

    public HomeAndAwayTeamAreTheSameException(String message) {
        super(message);
    }

    public HomeAndAwayTeamAreTheSameException(String message, Throwable cause) {
        super(message, cause);
    }

    public HomeAndAwayTeamAreTheSameException(Throwable cause) {
        super(cause);
    }

    protected HomeAndAwayTeamAreTheSameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
