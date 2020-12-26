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
import java.util.ArrayList;
import java.util.List;

//@WebServlet("/returnDataForStudent")
public class ReturnDataForOneStudent extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException, IOException {

        StudentDAO studentDAO;
        PrintWriter out = resp.getWriter();
        try {
            studentDAO = new StudentDAOImpl();
            List<Student> list = new ArrayList<>();
            list.addAll(studentDAO.findAll());

            resp.setContentType("text/html");


            out.println("<h1>Add Student</h1>");
            out.print("<form action='returnDataForStudent2' method='POST'>");
            out.println("<table>");
            out.println("<tr><td>Student:</td><td>");
            out.println("<select name='student' style='width:150px'>");
            for (Student student : list) {
                out.print("<option value=\"" + student.getId() + "\">" + student.getFirstName() + " " + student.getSecondName() + "</option>" );
            }
            out.println("</select>");
            out.println("</td></tr>");

            out.write("<p>");
            out.print("<tr><td colspan='2'><input type='submit' value='Get All Data for Selected Student'/></td></tr>");
            out.write("</p>");
            out.print("</table>");

            out.write("<p>");
            out.write("<a href=\"data\"><input type= \"button\" value= \"Press for return to previous page\"></a>");
            out.write("</p>");

            out.print("</form>");

            studentDAO.close();
            out.close();

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
