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

public class EditStudentController {

    private Validator validator = new Validator();

    public void OpenPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        boolean ifErrorFirstName;
        boolean ifErrorSecondName;
        Map<String, String> errors = new HashMap<>();

        String name=request.getParameter("First Name");
        String secondName=request.getParameter("Second Name");
        int id = Integer.parseInt(request.getParameter("id").trim());

        ifErrorFirstName = validator.CheckFieldForErrors(validator, name);
        if(ifErrorFirstName){
            errors.put("firstname", validator.ErrorMessage);
        }

        ifErrorSecondName = validator.CheckFieldForErrors(validator, secondName);
        if(ifErrorSecondName){
            errors.put("secondname", validator.ErrorMessage);
        }

        if(!ifErrorFirstName && !ifErrorSecondName) {
            try {
                Student student = new Student();
                student.setId(id);
                student.setFirstName(name);
                student.setSecondName(secondName);
                request.setAttribute("errorPresent", "false");
                StudentDAO studentDAO = new StudentDAOImpl();
                studentDAO.update(student);

                response.sendRedirect("students");
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
                request.setAttribute("errorPresent", "true");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/Views/EditStudentMain.jsp");
                requestDispatcher.forward(request, response);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }
    }
}
