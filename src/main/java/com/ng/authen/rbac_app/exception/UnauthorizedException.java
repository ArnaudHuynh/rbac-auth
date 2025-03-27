package com.ng.authen.rbac_app.exception;

/**
 * The type Unauthorized exception.
 */
public class UnauthorizedException extends RuntimeException {
    /**
     * Instantiates a new Unauthorized exception.
     *
     * @param message the message
     */
    public UnauthorizedException(String message) {
        super(message);
    }
}