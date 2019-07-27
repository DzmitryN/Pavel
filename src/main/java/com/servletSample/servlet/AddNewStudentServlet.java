package com.servletSample.servlet;

import dao.entities.impl.StudentDAOImpl;
import dao.interfaces.StudentDAO;
import entities.Student;
import exceptions.DAOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AddNewStudentServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException, IOException {
    try{
        resp.setContentType("text/html");

        String name=req.getParameter("First Name");
        String secondName=req.getParameter("Second Name");

        Student student = new Student();
        student.setFirstName(name);
        student.setSecondName(secondName);


        StudentDAO studentDAO = new StudentDAOImpl();
        studentDAO.save(student);

        resp.sendRedirect("students");

    } catch (
    DAOException e) {
        e.printStackTrace();
    }


}

    @Override

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException, IOException {

        doGet(req, resp);

    }
}
