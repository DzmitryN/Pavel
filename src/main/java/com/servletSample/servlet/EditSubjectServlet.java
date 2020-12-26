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


//@WebServlet("/editSubject2")
public class EditSubjectServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException, IOException {

        try{
            resp.setContentType("text/html");

            String sid=req.getParameter("id");
            int id=Integer.parseInt(sid);
            String subject=req.getParameter("Subject");

            Subject subject1 = new Subject();
            subject1.setId(id);
            subject1.setSubject(subject);

            SubjectDAO subjectDAO = new SubjectDAOImpl();
            subjectDAO.update(subject1);

            resp.sendRedirect("subjects");

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
