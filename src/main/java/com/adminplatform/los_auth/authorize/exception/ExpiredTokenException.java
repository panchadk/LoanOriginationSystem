package com.adminplatform.los_auth.authorize.exception;

public class ExpiredTokenException extends RuntimeException {
    public ExpiredTokenException(String message) {
        super(message);
    }
}
