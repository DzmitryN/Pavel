package controllers;

import dao.entities.impl.MarkDAOImpl;
import dao.entities.impl.StudentDAOImpl;
import dao.entities.impl.SubjectDAOImpl;
import dao.interfaces.MarkDAO;
import dao.interfaces.StudentDAO;
import dao.interfaces.SubjectDAO;
import entities.Mark;
import entities.Student;
import entities.Subject;
import exceptions.DAOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class AddSubjectAndMarkToStudentMainController {
        public void OpenPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            StudentDAO studentDAO = null;
            SubjectDAO subjectDAO = null;
            MarkDAO markDAO = null;
            try {
                studentDAO = new StudentDAOImpl();
                List<Student> list = new ArrayList<>();
                list.addAll(studentDAO.findAll(false));

                subjectDAO = new SubjectDAOImpl();
                List<Subject> listSubjects = new ArrayList<>();
                listSubjects.addAll(subjectDAO.findAll(false));


                markDAO = new MarkDAOImpl();
                List<Mark> markList = new ArrayList <>();
                markList.addAll(markDAO.findAll());

                request.setAttribute("studentsList", list);
                request.setAttribute("subjectList", listSubjects);
                request.setAttribute("marksList", markList);

                studentDAO.close();
                subjectDAO.close();
                markDAO.close();


            } catch (DAOException e) {
                e.printStackTrace();
            }
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/Views/AddSubjectAndMarkToStudentMain.jsp");
            requestDispatcher.forward(request, response);
        }
}
