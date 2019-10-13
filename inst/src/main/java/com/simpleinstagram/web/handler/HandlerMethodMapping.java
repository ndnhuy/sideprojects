package com.simpleinstagram.web.handler;

import com.simpleinstagram.exception.HandlerMappingException;
import com.simpleinstagram.exception.HandlerMethodInvokeException;
import com.simpleinstagram.web.BeanFactory;
import com.simpleinstagram.web.InitializingBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class HandlerMethodMapping implements HandlerMapping, InitializingBean {

    private Map<String, HandlerMethod> urlToHandlerMethod = new HashMap<>();
    private BeanFactory beanFactory;

    public HandlerMethodMapping(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public RequestHandler getHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HandlerMethod handlerMethod = urlToHandlerMethod.get(buildKey(request.getRequestURI(), request.getMethod()));
        if (handlerMethod == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            return new RequestHandlerMethod(handlerMethod.handler, handlerMethod.method);
        }
        return null;
    }

    @Override
    public void afterInitialize() {
        for (String beanName : beanFactory.getAllBeanNames()) {
            Object handler = beanFactory.getBean(beanName, Object.class);
            Class<?> handlerClazz = handler.getClass();
            if (handlerClazz.isAnnotationPresent(Controller.class)) {
                Method[] methods = handlerClazz.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping anno = method.getDeclaredAnnotation(RequestMapping.class);
                        String key = buildKey(anno.path(), anno.requestMethod().toString());
                        if (urlToHandlerMethod.containsKey(key)) {
                            throw new HandlerMappingException("Duplicate url");
                        }
                        urlToHandlerMethod.put(key, new HandlerMethod(method, handler));
                    }
                }
            }
        }
    }

    private String buildKey(String url, String requestMethod) {
        return url + "_" + requestMethod;
    }

    private class RequestHandlerMethod implements RequestHandler {

        private Object handler;
        private Method method;

        public RequestHandlerMethod(Object handler, Method method) {
            this.handler = handler;
            this.method = method;
        }

        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response) {
            try {
                this.method.invoke(handler, request, response);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new HandlerMethodInvokeException(String.format("Fail to invoke handler method %s", method), e);
            }
        }
    }


    class HandlerMethod {
        Method method;
        Object handler;

        HandlerMethod() {}
        HandlerMethod(Method method, Object handler) {
            this.method = method;
            this.handler = handler;
        }
    }
}
