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
import java.util.ArrayList;
import java.util.List;

public class ReturnAllSubjectsController {
    public void OpenPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try{
            SubjectDAO subjectDAO = new SubjectDAOImpl();
            List<Subject> listSubjects = new ArrayList<>();
            listSubjects.addAll(subjectDAO.findAll());
            request.setAttribute("subjects", listSubjects);
            subjectDAO.close();
        } catch (
            DAOException e) {
            e.printStackTrace();
    }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/Views/ReturnAllSubjects.jsp");
        requestDispatcher.forward(request, response);
    }
}
