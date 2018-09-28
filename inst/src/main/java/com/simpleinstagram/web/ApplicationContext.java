package com.simpleinstagram.web;

public class ApplicationContext implements BeanFactory {
    public <T> T getBean(String beanName, Class<T> requiredType) {
        Object bean = null;
        if ("handlerMapping".equalsIgnoreCase(beanName) && HandlerMapping.class.isAssignableFrom(requiredType)) {
            bean = new SimpleUrlHandlerMapping();
        }
        return (T) bean;
    }
}
