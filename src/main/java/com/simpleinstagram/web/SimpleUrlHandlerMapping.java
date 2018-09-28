package com.simpleinstagram.web;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import com.simpleinstagram.photo.PhotoUpload;

public class SimpleUrlHandlerMapping implements HandlerMapping {

    @Override
    public RequestHandler getHandler(HttpServletRequest request) {
        if ("/hello".equalsIgnoreCase(request.getRequestURI())) {
            return (req, resp) -> {
                Writer writer = resp.getWriter();
                writer.write("Hello222");
                writer.flush();
            };
        }
        if ("/photo".equalsIgnoreCase(request.getRequestURI()) && "GET".equalsIgnoreCase(request.getMethod())) {
            return new PhotoUpload()::doGet;
        }
        if ("/photo".equalsIgnoreCase(request.getRequestURI()) && "POST".equalsIgnoreCase(request.getMethod())) {
            return new PhotoUpload()::doPost;
        }
        return (req, resp) -> {};
    }

}
