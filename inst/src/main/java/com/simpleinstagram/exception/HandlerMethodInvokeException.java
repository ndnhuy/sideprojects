package com.simpleinstagram.exception;

public class HandlerMethodInvokeException extends RuntimeException {
    public HandlerMethodInvokeException(String message, Throwable cause) {
        super(message, cause);
    }
}
