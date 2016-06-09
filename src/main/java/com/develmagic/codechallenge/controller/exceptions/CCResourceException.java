package com.develmagic.codechallenge.controller.exceptions;

/**
 * CodeChallenge - 2016 (c) MartinFormanko 5/30/16.
 */
public abstract class CCResourceException extends RuntimeException {
    public CCResourceException() {
        super();
    }

    public CCResourceException(String message) {
        super(message);
    }

    public CCResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CCResourceException(Throwable cause) {
        super(cause);
    }

    protected CCResourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
