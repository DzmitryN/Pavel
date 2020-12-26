package controllers;

import dao.entities.impl.StudentDAOImpl;
import dao.interfaces.StudentDAO;
import exceptions.DAOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteStudentController {
    public void OpenPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            StudentDAO studentDAO = new StudentDAOImpl();
            studentDAO.delete(id);

            response.sendRedirect("students");
        }catch (DAOException e) {
            e.printStackTrace();
        }

    }
}
