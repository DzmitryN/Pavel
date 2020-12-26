package com.servletSample.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//@WebServlet("/addNewStudent")
public class  AddNewStudentMainServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException, IOException {

        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();
        out.println("<h1>Add Student</h1>");
        out.print("<form action='addNewStudent2' method='POST'>");

            out.print("<table>");
            out.print("<tr><td>First Name:</td><td><input type='text' name='First Name'/></td></tr>");
            out.print("<tr><td>Second Name:</td><td><input type='text' name='Second Name'/></td></tr>");
            out.print("<tr><td colspan='2'><input type='submit' value='Save New Student'/></td></tr>");
            out.print("</table>");

            out.write("<p>");
            out.write("<a href=\"students\"><input type= \"button\" value= \"Press for return to previous page\"></a>");
            out.write("</p>");

            out.print("</form>");

            out.close();
    }

    @Override

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException, IOException {

        doGet(req, resp);

    }

}
