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

public class EditStudentController {

    private Validator validator = new Validator();

    public void openPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Map<String, ArrayList<String>> errors = new HashMap <>();

        String name=request.getParameter("First Name");
        String secondName=request.getParameter("Second Name");
        int id = Integer.parseInt(request.getParameter("id").trim());

        ArrayList<String> firstNameErrors= validator.checkFieldForErrors(name);
        if(firstNameErrors!= null && firstNameErrors.size() > 0){
            errors.put("firstName", firstNameErrors);
        }

        ArrayList<String> lastNameErrors = validator.checkFieldForErrors(secondName);
        if(lastNameErrors != null && lastNameErrors.size() > 0 ){
            errors.put("secondName", lastNameErrors);
        }

        if(errors.size() == 0) {
            try {
                Student student = new Student();
                student.setId(id);
                student.setFirstName(name);
                student.setSecondName(secondName);
                StudentDAO studentDAO = new StudentDAOImpl();
                studentDAO.update(student);

                request.getRequestDispatcher("/students").forward(request, response);
                studentDAO.close();
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }
        else{
            try{
                StudentDAO studentDAO = new StudentDAOImpl();
                Student student = studentDAO.getById(id);
                request.setAttribute("studentDTO", student);
                studentDAO.close();
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("/WEB-INF/Views/EditStudentMain.jsp").forward(request, response);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }
    }
}
