package com.servletSample.servlet;

import utils.Dispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "FrontServlet", urlPatterns = {"/"}, loadOnStartup = 1)
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException, IOException {

        String path = req.getRequestURI().substring(req.getContextPath().length()).toUpperCase();

        Dispatcher dispatcher = new Dispatcher();
        dispatcher.Dispatch(path, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException, IOException {
        doGet(req, resp);
    }
}
