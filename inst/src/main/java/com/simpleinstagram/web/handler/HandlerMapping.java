package com.simpleinstagram.web.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerMapping {
    RequestHandler getHandler(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
