package controllers;

import dao.entities.impl.SubjectDAOImpl;
import dao.interfaces.SubjectDAO;
import entities.Subject;
import exceptions.DAOException;
import utils.Validator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReturnAllSubjectsController {
    Validator validator = new Validator();
    public void OpenPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int start =0;
        try {
            try{
                start = Integer.parseInt(request.getParameter("start"));
            }catch(Exception e){}

            SubjectDAO subjectDAO = new SubjectDAOImpl(start);
            List<Subject> listSubjects = new ArrayList<>();
            listSubjects.addAll(subjectDAO.findAll(true));
            request.setAttribute("subjects", listSubjects);
            String pages = "" + validator.GetNumberOfPages(subjectDAO.pagination());
            request.setAttribute("pages", pages);
            subjectDAO.close();
        } catch (
            DAOException e) {
            e.printStackTrace();
    }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/Views/ReturnAllSubjects.jsp");
        requestDispatcher.forward(request, response);
    }
}
