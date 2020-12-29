package com.servletSample.servlet;

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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/addNewDataToStudent")
public class AddSubjectAndMarkToStudentMainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException, IOException {

        StudentDAO studentDAO = null;
        SubjectDAO subjectDAO = null;
        MarkDAO markDAO = null;
        PrintWriter out = resp.getWriter();
        try {
            studentDAO = new StudentDAOImpl();
            List<Student> list = new ArrayList<>();
            list.addAll(studentDAO.findAll());

            subjectDAO = new SubjectDAOImpl();
            List<Subject> listSubjects = new ArrayList<>();
            listSubjects.addAll(subjectDAO.findAll());


            markDAO = new MarkDAOImpl();
            List<Mark> markList = new ArrayList <>();
            markList.addAll(markDAO.findAll());

        resp.setContentType("text/html");


        out.println("<h1>Add Subject And Mark For The Student</h1>");
        out.print("<form action='addNewDataToStudent2' method='POST'>");
        out.println("<table>");
        out.println("<tr><td>Student:</td><td>");
        out.println("<select name='student' style='width:150px'>");
            for (Student student : list) {
                out.print("<option value=\"" + student.getId() + "\">" + student.getFirstName() + " " + student.getSecondName() + "</option>" );
            }
            out.println("</select>");
            out.println("</td></tr>");


            out.println("<tr><td>Subject:</td><td>");
            out.println("<select name='subject' style='width:150px'>");
            for (Subject subject : listSubjects){
                out.print("<option value=\"" + subject.getId() + "\">" + subject.getSubject() + "</option>" );
            }
            out.println("</select>");
            out.println("</td></tr>");


            out.println("<tr><td>Mark:</td><td>");
            out.println("<select name='mark' style='width:150px'>");
            for (Mark mark : markList){
                out.print("<option value=\"" + mark.getID() + "\">" + mark.getMark() + "</option>" );
            }
            out.println("</select>");
            out.println("</td></tr>");



            out.write("<p>");
            out.print("<tr><td colspan='2'><input type='submit' value='Save Changes'/></td></tr>");
            out.write("</p>");
        out.print("</table>");

        out.write("<p>");
        out.write("<a href=\"data\"><input type= \"button\" value= \"Press for return to previous page\"></a>");
        out.write("</p>");

        out.print("</form>");

        studentDAO.close();
        subjectDAO.close();
        out.close();

            } catch (DAOException e) {
                e.printStackTrace();
            }


    }

    @Override

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException, IOException {

        doGet(req, resp);


    }
}
