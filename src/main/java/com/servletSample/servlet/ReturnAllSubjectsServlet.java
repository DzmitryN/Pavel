package com.servletSample.servlet;

import dao.entities.impl.SubjectDAOImpl;
import dao.interfaces.SubjectDAO;
import entities.Subject;
import exceptions.DAOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ReturnAllSubjectsServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<h1>All Subjects</h1>");
        out.print("<table border='1' width='100%'");
        out.print("<tr><th>Id</th><th>Subject</th><th>Edit</th></tr>");

        try {

            SubjectDAO subjectDAO = new SubjectDAOImpl();
            List<Subject> listSubjects = new ArrayList<>();
            listSubjects.addAll(subjectDAO.findAll());
            for (Subject subject : listSubjects){
                out.print("<tr><td>"+ subject.getId()+"</td><td>"+subject.getSubject()+"</td>" +
                        "<td><a href='EditSubjectServlet?id="+subject.getId()+"'>edit</a></td></tr>");
            }
            subjectDAO.close();
        } catch (DAOException e) {
            e.printStackTrace();
        }

        out.print("</table>");

        out.write("<p>");
        out.write("<a href=\"/\"><input type= \"button\" value= \"Press for return to start page!\"></a>");
        out.write("</p>");
    }
    @Override

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException, IOException {

        doGet(req, resp);

    }

}
