package com.simpleinstagram.web.handler;

import java.lang.annotation.*;

import static com.simpleinstagram.web.handler.RequestMethod.GET;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
	String path() default "";
	RequestMethod requestMethod() default GET;
}
