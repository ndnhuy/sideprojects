package com.simpleinstagram.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
    void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException;
    void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}
