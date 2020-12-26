package com.servletSample.servlet;

import dao.entities.impl.StudentDAOImpl;
import dao.interfaces.StudentDAO;
import entities.Student;
import exceptions.DAOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//@WebServlet("/editStudent")
public class EditStudentMainServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException, IOException {

        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();

        out.println("<h1>Update Student</h1>");
        out.print("<form action='editStudent2' method='POST'>");
        String sid = req.getParameter("id");
        int id = Integer.parseInt(sid);
        try {

            StudentDAO studentDAO = new StudentDAOImpl();
            Student student = studentDAO.getById(id);

            out.print("<table>");
            out.print("<tr><td></td><td><input type='hidden' name='id' value='"+student.getId()+"'/></td></tr>");
            out.print("<tr><td>Name:</td><td><input type='text' name='First Name' value='"+student.getFirstName()+"'/></td></tr>");
            out.print("<tr><td>Second Name:</td><td><input type='text' name='Second Name' value='"+student.getSecondName()+"'/></td></tr>");
            out.print("<tr><td colspan='2'><input type='submit' value='Edit & Save '/></td></tr>");
            out.print("</table>");

            out.write("<p>");
            out.write("<a href=\"students\"><input type= \"button\" value= \"Press for return to previous page\"></a>");
            out.write("</p>");

            out.print("</form>");

            out.close();
            studentDAO.close();
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

        @Override

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException, IOException {

        doGet(req, resp);

    }
}
