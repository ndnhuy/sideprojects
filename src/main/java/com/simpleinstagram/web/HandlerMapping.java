package com.simpleinstagram.web;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
    RequestHandler getHandler(HttpServletRequest request);
}
