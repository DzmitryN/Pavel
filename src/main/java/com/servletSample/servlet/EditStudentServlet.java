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

@WebServlet("/editStudent2")
public class EditStudentServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException, IOException {

        try{
            resp.setContentType("text/html");

            String sid=req.getParameter("id");
            int id=Integer.parseInt(sid);
            String name=req.getParameter("First Name");
            String secondName=req.getParameter("Second Name");

            Student student = new Student();
            student.setId(id);
            student.setFirstName(name);
            student.setSecondName(secondName);


            StudentDAO studentDAO = new StudentDAOImpl();
            studentDAO.update(student);

            resp.sendRedirect("students");

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
