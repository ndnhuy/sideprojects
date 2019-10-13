package com.simpleinstagram.web;

import com.simpleinstagram.exception.NoHandlerFoundException;
import com.simpleinstagram.web.handler.HandlerMapping;
import com.simpleinstagram.web.handler.RequestHandler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DispatcherServlet extends HttpServlet {
    private BeanFactory beanFactory;
    public DispatcherServlet(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HandlerMapping handlerMapping = beanFactory.getBean("handlerMapping", HandlerMapping.class);
            RequestHandler requestHandler = handlerMapping.getHandler(req, resp);
            if (requestHandler == null) {
                throw new NoHandlerFoundException(req.getMethod(), req.getRequestURI());
            }
            requestHandler.handle(req, resp);
        } catch (ServletException | IOException ex) {
            throw ex;
        } catch (Throwable ex) {
            throw new ServletException("Request processing fail");
        }
    }
}
