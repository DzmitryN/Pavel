package controllers;

import dao.entities.impl.SubjectDAOImpl;
import dao.interfaces.SubjectDAO;
import entities.Subject;
import exceptions.DAOException;
import utils.Paginator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReturnAllSubjectsController {
    Paginator paginator = new Paginator();
    public void openPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int start =0;
        try {
            try{
                start = Integer.parseInt(request.getParameter("start"));
            }catch(Exception e){}

            SubjectDAO subjectDAO = new SubjectDAOImpl();
            List<Subject> listSubjects = new ArrayList<>();
            listSubjects.addAll(subjectDAO.findAll(start));
            request.setAttribute("subjects", listSubjects);
            String pages = "" + paginator.getNumberOfPages(subjectDAO.pagination());
            request.setAttribute("pages", pages);
            subjectDAO.close();
        } catch (
            DAOException e) {
            e.printStackTrace();
    }
        request.getRequestDispatcher("/WEB-INF/Views/ReturnAllSubjects.jsp").forward(request, response);
    }
}
