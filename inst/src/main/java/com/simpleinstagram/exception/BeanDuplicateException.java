package com.simpleinstagram.exception;

public class BeanDuplicateException extends RuntimeException {

    public BeanDuplicateException(String message) {
        super(message);
    }

    public BeanDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }
}
