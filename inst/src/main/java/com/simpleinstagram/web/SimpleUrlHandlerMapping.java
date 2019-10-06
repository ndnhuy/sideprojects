package com.simpleinstagram.web;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import com.simpleinstagram.photo.PhotoUploadController;
import jdk.internal.org.objectweb.asm.commons.SimpleRemapper;

public class SimpleUrlHandlerMapping implements HandlerMapping {

    private BeanFactory beanFactory;

    public SimpleUrlHandlerMapping(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

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
            return beanFactory.getBean("photoUploadController", ControllerAdapter.class)::doGet;
        }
        if ("/photo".equalsIgnoreCase(request.getRequestURI()) && "POST".equalsIgnoreCase(request.getMethod())) {
            return beanFactory.getBean("photoUploadController", ControllerAdapter.class)::doPost;
        }
        return (req, resp) -> {};
    }

}
