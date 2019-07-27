package com.servletSample.servlet;


import dao.entities.impl.MarkSheetDAOImpl;
import dao.entities.impl.StudentDAOImpl;
import dao.interfaces.Marks_SheetDAO;
import dao.interfaces.StudentDAO;
import entities.MarksSheet;
import entities.Student;
import exceptions.DAOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class RetunAllStudentsServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException, IOException {

        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();
        out.println("<h1>All Students</h1>");
        out.print("<table border='1' width='100%'");
        out.print("<tr><th>Name</th><th>Second Name</th><th>Edit</th><th>Delete</th></tr>");

        try {
            StudentDAO studentDAO = new StudentDAOImpl();
            List<Student> list = new ArrayList<>();
            list.addAll(studentDAO.findAll());
            for (Student student : list){
                out.print("<tr><td>"+student.getFirstName()+"</td><td>"+student.getSecondName()+"</td>" +
                        "<td><a href='editStudent?id="+student.getId()+"'>Edit</a></td>" +
                        "<td><a href='deleteStudent?id="+student.getId()+"'>Delete</a></td></tr>");
            }
            studentDAO.close();
        } catch (DAOException e) {
            e.printStackTrace();
        }

        out.print("</table>");
        out.write("<p>");
        out.write("<a href=\"addNewStudent\"><input type= \"button\" value= \"Press for add new Student\"></a>");
        out.write("</p>");

        out.write("<p>");
        out.write("<a href=\"/\"><input type= \"button\" value= \"Press for return to start page\"></a>");
        out.write("</p>");
        out.close();
    }

    @Override

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException, IOException {

        doGet(req, resp);

    }
}
