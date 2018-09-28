package com.simpleinstagram.web;

public interface BeanFactory {
    public <T> T getBean(String beanName, Class<T> requiredType);
}
