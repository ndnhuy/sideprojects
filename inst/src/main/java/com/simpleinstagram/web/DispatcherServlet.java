package com.simpleinstagram.web;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DispatcherServlet extends HttpServlet {
    private BeanFactory beanFactory;
    public DispatcherServlet(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HandlerMapping handlerMapping = beanFactory.getBean("handlerMapping", HandlerMapping.class);
        RequestHandler requestHandler = handlerMapping.getHandler(req);
        requestHandler.handle(req, resp);
    }
}
