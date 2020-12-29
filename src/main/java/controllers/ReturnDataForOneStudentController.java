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
import java.util.ArrayList;
import java.util.List;

public class ReturnDataForOneStudentController {
    public void OpenPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        StudentDAO studentDAO;
        try {
            studentDAO = new StudentDAOImpl();
            List<Student> list = new ArrayList<>();
            list.addAll(studentDAO.findAll());
            request.setAttribute("students", list);
            studentDAO.close();
        } catch (DAOException e) {
            e.printStackTrace();
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/Views/ReturnDataForOneStudent.jsp");
        requestDispatcher.forward(request, response);
    }
}
