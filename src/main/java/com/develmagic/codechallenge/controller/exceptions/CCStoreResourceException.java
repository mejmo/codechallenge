package com.develmagic.codechallenge.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * CodeChallenge - 2016 (c) MartinFormanko 5/31/16.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class CCStoreResourceException extends CCResourceException {
    public CCStoreResourceException() {
    }

    public CCStoreResourceException(String message) {
        super(message);
    }

    public CCStoreResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CCStoreResourceException(Throwable cause) {
        super(cause);
    }

    public CCStoreResourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
