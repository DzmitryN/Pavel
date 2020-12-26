package com.servletSample.servlet;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("/hello.jsp")
public class HelloServlet
     extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException, IOException {

        //resp.setContentType("text/html");
        /*PrintWriter out = resp.getWriter();
        out.write("Hello Everybody!");
        out.write("<p>");
        out.write("<a href=\"/\"><input type= \"button\" value= \"Press for return to start page!\"></a>");
        out.write("</p>");
        out.close();*/
        //resp.sendRedirect("/Hello.jsp");
        getServletContext().getRequestDispatcher("/Hello.jsp").forward(req, resp);
    }

    /*@Override

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException, IOException {

        doGet(req, resp);

    }*/
}
