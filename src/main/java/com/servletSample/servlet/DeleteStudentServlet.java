package com.servletSample.servlet;


import dao.entities.impl.StudentDAOImpl;
import dao.interfaces.StudentDAO;
import exceptions.DAOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteStudentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException, IOException {
        try {
            String sid = req.getParameter("id");
            int id = Integer.parseInt(sid);
            StudentDAO studentDAO = new StudentDAOImpl();
            studentDAO.delete(id);

            resp.sendRedirect("students");
        }catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Override

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException, IOException {

        doGet(req, resp);

    }




}
