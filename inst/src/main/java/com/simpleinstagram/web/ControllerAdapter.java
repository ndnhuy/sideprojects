package com.simpleinstagram.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ControllerAdapter implements Controller {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        throw new UnsupportedOperationException("Request method GET not supported");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Request method POST not supported");
    }
}
