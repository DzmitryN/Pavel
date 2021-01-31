package controllers;

import dao.entities.impl.StudentDAOImpl;
import dao.interfaces.StudentDAO;
import exceptions.DAOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteStudentController {
    public void openPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            StudentDAO studentDAO = new StudentDAOImpl();
            studentDAO.delete(id);

            request.getRequestDispatcher("/students").forward(request, response);
            studentDAO.close();
        }catch (DAOException e) {
            e.printStackTrace();
        }

    }
}
