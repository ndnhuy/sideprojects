package com.simpleinstagram.exception;

import javax.servlet.ServletException;

public class NoHandlerFoundException extends ServletException {
    public NoHandlerFoundException(String message) {
        super(message);
    }

    public NoHandlerFoundException(String httpMethod, String requestURL) {
        super("No handler found for " + httpMethod + " " + requestURL);
    }
}
