package com.servletSample.servlet;



import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class MainServlet extends HttpServlet {

    @Override

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.write("<html>");
        out.write("<head>");
        out.write("<title>Next trial</title>");
        out.write("</head>");
        out.write("<body>");
        out.write("Let`s go!!! Just do it!!!");
        out.write("<p>");
        out.write("<a href=\"hello\"><input type= \"button\"  value= \"Press for hello!\"></a>");
        out.write("</p>");
        out.write("<p>");
        out.write("<a href=\"students\"><input type= \"button\" value= \"Press for getting all students!\"></a>");
        out.write("</p>");
        out.write("<p>");
        out.write("<a href=\"subjects\"><input type= \"button\" value= \"Press for getting all subjects!\"></a>");
        out.write("</p>");

        out.write("</body>");
        out.write("</html>");

    }

    @Override

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException, IOException {

        super.doPost(req, resp);

    }
}
