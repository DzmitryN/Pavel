package controllers;

import dao.entities.impl.StudentDAOImpl;
import dao.interfaces.StudentDAO;
import entities.Student;
import exceptions.DAOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditStudentMainController {
    public void openPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        int sid = Integer.parseInt((request.getParameter("id")));
        try{
            StudentDAO studentDAO = new StudentDAOImpl();
            Student student = studentDAO.getById(sid);
            request.setAttribute("studentDTO", student);
            studentDAO.close();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/WEB-INF/Views/EditStudentMain.jsp").forward(request, response);
    }
}
