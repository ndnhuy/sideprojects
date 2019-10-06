package com.simpleinstagram.web;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.simpleinstagram.exception.BeanNotFoundException;
import com.simpleinstagram.photo.PhotoDAO;
import com.simpleinstagram.photo.PhotoService;
import com.simpleinstagram.photo.PhotoUploadController;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ApplicationContext implements BeanFactory {

    private static Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    public ApplicationContext() {
        init();
    }

    public void init() {
        registerBean("handlerMapping", HandlerMapping.class, new SimpleUrlHandlerMapping(this));
        registerBean("dataSource", DataSource.class, mysqlDataSource());
        registerBean("photoService", PhotoService.class, new PhotoService(new PhotoDAO(this.getBean("dataSource"))));
        registerBean("photoUploadController",
                ControllerAdapter.class,
                new PhotoUploadController(this.getBean("photoService", PhotoService.class)));
    }

    private MysqlDataSource mysqlDataSource() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL("jdbc:mysql://localhost:3306/instagram");
        mysqlDataSource.setUser("root");
        mysqlDataSource.setPassword("root");
        return mysqlDataSource;
    }

    private static <T> void registerBean(String beanName, Class<T> clazz, T instance) {
        beanDefinitionMap.put(beanName, new BeanDefinition(beanName, clazz, instance));
    }

    public <T> T getBean(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new BeanNotFoundException(String.format("Bean %s not found", beanName));
        }
        return (T) beanDefinition.instance;
    }

    public <T> T getBean(String beanName, Class<T> requiredType) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null || !beanDefinition.classType.isAssignableFrom(requiredType)) {
            throw new BeanNotFoundException(String.format("Bean %s not found for required type %s", beanName, requiredType));
        }
        return (T) beanDefinition.instance;
    }

    private static class BeanDefinition<T> {
        String beanName;
        Class<T> classType;
        T instance;

        public BeanDefinition(String beanName, Class<T> classType, T instance) {
            this.beanName = beanName;
            this.classType = classType;
            this.instance = instance;
        }
    }
}
