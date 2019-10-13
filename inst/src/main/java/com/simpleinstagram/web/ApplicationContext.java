package com.simpleinstagram.web;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.simpleinstagram.exception.BeanDuplicateException;
import com.simpleinstagram.exception.BeanNotFoundException;
import com.simpleinstagram.photo.PhotoDAO;
import com.simpleinstagram.photo.PhotoService;
import com.simpleinstagram.photo.PhotoController;
import com.simpleinstagram.web.handler.HandlerMapping;
import com.simpleinstagram.web.handler.HandlerMethodMapping;

import javax.sql.DataSource;
import java.util.*;

public class ApplicationContext implements BeanFactory {

    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    private List<String> beanNames = new ArrayList<>();

    public ApplicationContext() {
        init();
    }

    public void init() {
        registerBean("handlerMapping", HandlerMapping.class, new HandlerMethodMapping(this));
        registerBean("dataSource", DataSource.class, mysqlDataSource());
        registerBean("photoService", PhotoService.class, new PhotoService(new PhotoDAO(this.getBean("dataSource"))));
        registerBean("photoUploadController",
                ControllerAdapter.class,
                new PhotoController(this.getBean("photoService", PhotoService.class)));
        beanDefinitionMap.values().stream().map(def -> def.instance).filter(o -> o instanceof InitializingBean)
            .forEach(o -> ((InitializingBean) o).afterInitialize());
    }

    private MysqlDataSource mysqlDataSource() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL("jdbc:mysql://localhost:3306/instagram");
        mysqlDataSource.setUser("root");
        mysqlDataSource.setPassword("root");
        return mysqlDataSource;
    }

    private <T> void registerBean(String beanName, Class<T> clazz, T instance) {
        if (beanDefinitionMap.containsKey(beanName)) {
            throw new BeanDuplicateException("Duplicate bean while registering");
        }
        beanDefinitionMap.put(beanName, new BeanDefinition(beanName, clazz, instance));
        beanNames.add(beanName);
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
        if (beanDefinition == null || !requiredType.isAssignableFrom(beanDefinition.classType)) {
            throw new BeanNotFoundException(String.format("Bean %s not found for required type %s", beanName, requiredType));
        }
        return (T) beanDefinition.instance;
    }

    @Override
    public List<String> getAllBeanNames() {
        return Collections.unmodifiableList(this.beanNames);
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
