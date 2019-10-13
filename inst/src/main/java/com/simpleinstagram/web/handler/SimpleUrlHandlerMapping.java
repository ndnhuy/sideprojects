package com.simpleinstagram.web.handler;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.simpleinstagram.web.BeanFactory;
import com.simpleinstagram.web.ControllerAdapter;

/**
 *  Return corresponding handler for a specific url
 */
public class SimpleUrlHandlerMapping implements HandlerMapping {

    private BeanFactory beanFactory;

    public SimpleUrlHandlerMapping(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public RequestHandler getHandler(HttpServletRequest request, HttpServletResponse response) {
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
