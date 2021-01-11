package controllers;

import dao.entities.impl.StudentDAOImpl;
import dao.interfaces.StudentDAO;
import entities.Student;
import exceptions.DAOException;
import utils.Validator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddNewStudentController {

    private Validator validator = new Validator();

    public void OpenPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        boolean ifErrorFirstName;
        boolean ifErrorSecondName;
        Map<String, String>  errors = new HashMap <>();


        String name=request.getParameter("First Name");
        String secondName = request.getParameter("Second Name");

        ifErrorFirstName = validator.CheckFieldForErrors(validator, name);
        if(ifErrorFirstName){
            errors.put("firstname", validator.ErrorMessage);
        }

        ifErrorSecondName = validator.CheckFieldForErrors(validator, secondName);
        if(ifErrorSecondName){
            errors.put("secondname", validator.ErrorMessage);
        }

        if(!ifErrorFirstName && !ifErrorSecondName) {
            Student student = new Student();
            student.setFirstName(name);
            student.setSecondName(secondName);
            request.setAttribute("errorPresent", "false");
            try {
                StudentDAO studentDAO = new StudentDAOImpl();
                studentDAO.save(student);
                response.sendRedirect("students");
                studentDAO.close();
            } catch (
                    DAOException e) {
                e.printStackTrace();
            }
        }else{
            request.setAttribute("errors", errors);
            request.setAttribute("errorPresent", "true");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/Views/AddNewStudentMain.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
