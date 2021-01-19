package controllers;

import dao.entities.impl.StudentDAOImpl;
import dao.interfaces.StudentDAO;
import entities.Student;
import exceptions.DAOException;
import utils.Validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddNewStudentController {

    private Validator validator = new Validator();

    public void openPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String name=request.getParameter("First Name");
        String secondName = request.getParameter("Second Name");

        Map<String, ArrayList<String>> errors = new HashMap <>();

        ArrayList<String> firstNameErrors= validator.checkFieldForErrors(name);
        if(firstNameErrors!= null && firstNameErrors.size() > 0){
            errors.put("firstName", firstNameErrors);
        }

        ArrayList<String> lastNameErrors = validator.checkFieldForErrors(secondName);
        if(lastNameErrors != null && lastNameErrors.size() > 0 ){
            errors.put("secondName", lastNameErrors);
        }

        if(errors.size() == 0) {
            Student student = new Student();
            student.setFirstName(name);
            student.setSecondName(secondName);
            try {
                StudentDAO studentDAO = new StudentDAOImpl();
                studentDAO.save(student);
                request.getRequestDispatcher("/students").forward(request, response);
                studentDAO.close();
            } catch (
                    DAOException e) {
                e.printStackTrace();
            }
        }else{
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/WEB-INF/Views/AddNewStudentMain.jsp").forward(request, response);
        }
    }
}
