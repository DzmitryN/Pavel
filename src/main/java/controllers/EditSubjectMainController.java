package controllers;


import dao.entities.impl.SubjectDAOImpl;
import dao.interfaces.SubjectDAO;
import entities.Subject;
import exceptions.DAOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditSubjectMainController {
    public void OpenPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        try {

            SubjectDAO subjectDAO = new SubjectDAOImpl();
            Subject subject = subjectDAO.getById(id);
            request.setAttribute("subjectDTO", subject);
            subjectDAO.close();

        } catch (DAOException e) {
            e.printStackTrace();
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/Views/EditSubjectMain.jsp");
        requestDispatcher.forward(request, response);
    }
}
