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
import java.util.ArrayList;
import java.util.List;

public class ReturnAllStudentsController {
    Validator validator = new Validator();
    public void OpenPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    int start =0;
        try {
            try{
                start = Integer.parseInt(request.getParameter("start"));
            }catch(Exception e){}

            StudentDAO studentDAO = new StudentDAOImpl(start);
            List<Student> list = new ArrayList<>();
            list.addAll(studentDAO.findAll(true));
            request.setAttribute("students", list);
            String pages = "" + validator.GetNumberOfPages(studentDAO.pagination());
            request.setAttribute("pages", pages);
            studentDAO.close();
        } catch (DAOException e) {
            e.printStackTrace();
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/Views/ReturnAllStudents.jsp");
        requestDispatcher.forward(request, response);
    }
}
