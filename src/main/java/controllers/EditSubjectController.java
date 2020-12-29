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

public class EditSubjectController {
    public void OpenPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try{
            int id=Integer.parseInt(request.getParameter("id"));
            String subject=request.getParameter("Subject");

            Subject subject1 = new Subject();
            subject1.setId(id);
            subject1.setSubject(subject);

            SubjectDAO subjectDAO = new SubjectDAOImpl();
            subjectDAO.update(subject1);

            response.sendRedirect("subjects");
            subjectDAO.close();

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
