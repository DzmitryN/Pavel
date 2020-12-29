package controllers;


import dao.entities.impl.StudentDAOImpl;
import dao.interfaces.StudentDAO;
import entities.Student;
import exceptions.DAOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditStudentController {
    public void OpenPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        try{

            int id=Integer.parseInt(request.getParameter("id"));
            String name=request.getParameter("First Name");
            String secondName=request.getParameter("Second Name");

            Student student = new Student();
            student.setId(id);
            student.setFirstName(name);
            student.setSecondName(secondName);


            StudentDAO studentDAO = new StudentDAOImpl();
            studentDAO.update(student);

            response.sendRedirect("students");
            studentDAO.close();

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
