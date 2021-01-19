package controllers;

import dao.entities.impl.SubjectDAOImpl;
import dao.interfaces.SubjectDAO;
import entities.Subject;
import exceptions.DAOException;
import utils.Validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditSubjectController {

    private Validator validator = new Validator();

    public void openPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Map<String, ArrayList<String>> errors = new HashMap <>();

        int id=Integer.parseInt(request.getParameter("id"));
        String subject=request.getParameter("Subject");
        ArrayList<String> errorsSubject = validator.checkFieldForErrors(subject);

        if(errorsSubject != null && errorsSubject.size() > 0){
            errors.put("subject", errorsSubject);
        }

        if(errors.size() == 0) {
            try{
                Subject subjectDTO = new Subject();
                subjectDTO.setId(id);
                subjectDTO.setSubject(subject);
                SubjectDAO subjectDAO = new SubjectDAOImpl();
                subjectDAO.update(subjectDTO);
                request.getRequestDispatcher("/subjects").forward(request, response);
                subjectDAO.close();
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }else{
            try{
                SubjectDAO subjectDAO = new SubjectDAOImpl();
                Subject subjectDTO = subjectDAO.getById(id);
                request.setAttribute("subjectDTO", subjectDTO);
                subjectDAO.close();
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("/WEB-INF/Views/EditSubjectMain.jsp").forward(request, response);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }
    }
}
