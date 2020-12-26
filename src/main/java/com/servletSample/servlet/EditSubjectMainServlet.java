package com.servletSample.servlet;

import dao.entities.impl.SubjectDAOImpl;
import dao.interfaces.SubjectDAO;
import entities.Subject;
import exceptions.DAOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//@WebServlet("/EditSubjectServlet")
public class EditSubjectMainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException, IOException {

        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();

        out.println("<h1>Update Subject</h1>");
        out.print("<form action='editSubject2' method='POST'>");
        String sid = req.getParameter("id");
        int id = Integer.parseInt(sid);
        try {

            SubjectDAO subjectDAO = new SubjectDAOImpl();
            Subject subject = subjectDAO.getById(id);

            out.print("<table>");
            out.print("<tr><td></td><td><input type='hidden' name='id' value='"+subject.getId()+"'/></td></tr>");
            out.print("<tr><td>Name:</td><td><input type='text' name='Subject' value='"+subject.getSubject()+"'/></td></tr>");
            out.print("<tr><td colspan='2'><input type='submit' value='Edit & Save '/></td></tr>");
            out.print("</table>");

            out.write("<p>");
            out.write("<a href=\"subjects\"><input type= \"button\" value= \"Press for return to previous page\"></a>");
            out.write("</p>");

            out.print("</form>");
            subjectDAO.close();
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
