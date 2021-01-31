package controllers;

import dao.entities.impl.StudentDAOImpl;
import dao.interfaces.StudentDAO;
import entities.Student;
import exceptions.DAOException;
import utils.Paginator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReturnAllStudentsController {
    Paginator paginator = new Paginator();
    public void openPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    int start =0;
        try {
            try{
                start = Integer.parseInt(request.getParameter("start"));
            }catch(Exception e){}

            StudentDAO studentDAO = new StudentDAOImpl();
            List<Student> list = new ArrayList<>();
            list.addAll(studentDAO.findAll(start));
            request.setAttribute("students", list);
            String pages = "" + paginator.getNumberOfPages(studentDAO.pagination());
            request.setAttribute("pages", pages);
            studentDAO.close();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/WEB-INF/Views/ReturnAllStudents.jsp").forward(request, response);
    }
}
