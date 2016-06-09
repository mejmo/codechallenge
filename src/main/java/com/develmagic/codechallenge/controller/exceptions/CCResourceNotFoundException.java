package com.develmagic.codechallenge.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * CodeChallenge - 2016 (c) MartinFormanko 5/30/16.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CCResourceNotFoundException extends CCResourceException {
    public CCResourceNotFoundException() {
        super();
    }

    public CCResourceNotFoundException(String message) {
        super(message);
    }

    public CCResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CCResourceNotFoundException(Throwable cause) {
        super(cause);
    }

    protected CCResourceNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
