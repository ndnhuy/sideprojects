package com.simpleinstagram.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@FunctionalInterface
public interface RequestHandler {
    void handle(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
