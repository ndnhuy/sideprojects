package com.simpleinstagram.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerAdapter implements Controller {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        throw new UnsupportedOperationException("Request method GET not supported");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        throw new UnsupportedOperationException("Request method POST not supported");
    }
}
