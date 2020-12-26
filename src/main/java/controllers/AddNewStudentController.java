package controllers;

import dao.entities.impl.StudentDAOImpl;
import dao.interfaces.StudentDAO;
import entities.Student;
import exceptions.DAOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddNewStudentController {
    public void OpenPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name=request.getParameter("First Name");
        String secondName=request.getParameter("Second Name");

        Student student = new Student();
        student.setFirstName(name);
        student.setSecondName(secondName);

        try{
        StudentDAO studentDAO = new StudentDAOImpl();
        studentDAO.save(student);

        response.sendRedirect("students");
        } catch (
                DAOException e) {
            e.printStackTrace();
        }
        //RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/Views/AddNewStudent.jsp");
        //requestDispatcher.forward(request, response);
    }
}
