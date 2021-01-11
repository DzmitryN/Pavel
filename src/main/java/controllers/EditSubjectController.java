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
import java.util.HashMap;
import java.util.Map;

public class EditSubjectController {

    private Validator validator = new Validator();

    public void OpenPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        boolean ifErrorSubject;
        Map<String, String> errors = new HashMap<>();

        int id=Integer.parseInt(request.getParameter("id"));
        String subject=request.getParameter("Subject");
        ifErrorSubject = validator.CheckFieldForErrors(validator, subject);

        if(ifErrorSubject){
            errors.put("subject", validator.ErrorMessage);
        }

        if(!ifErrorSubject) {
            try{
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
        }else{
            try{
                SubjectDAO subjectDAO = new SubjectDAOImpl();
                Subject _subject = subjectDAO.getById(id);
                request.setAttribute("subjectDTO", _subject);
                subjectDAO.close();
                request.setAttribute("errors", errors);
                request.setAttribute("errorPresent", "true");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/Views/EditSubjectMain.jsp");
                requestDispatcher.forward(request, response);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }
    }
}
