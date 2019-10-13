package com.simpleinstagram.web;

import java.util.List;

public interface BeanFactory {
    <T> T getBean(String beanName, Class<T> requiredType);
    List<String> getAllBeanNames();
}
