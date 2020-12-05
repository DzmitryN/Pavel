package com.servletSample.servlet;

import dao.entities.impl.MarkSheetDAOImpl;
import dao.entities.impl.StudentDAOImpl;
import dao.interfaces.Marks_SheetDAO;
import dao.interfaces.StudentDAO;
import entities.MarksSheet;
import entities.Student;
import exceptions.DAOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/addNewDataToStudent2")
public class AddSubjectAndMarkToStudentServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException, IOException {

        try{
            resp.setContentType("text/html");

            String studentId=req.getParameter("student");
            int sId=Integer.parseInt(studentId);

            String subjectId=req.getParameter("subject");
            int sbId=Integer.parseInt(subjectId);

            String markId=req.getParameter("mark");
            int mId=Integer.parseInt(markId);

            MarksSheet marksSheet = new MarksSheet();
            marksSheet.setStudent_id(sId);
            marksSheet.setSubject_id(sbId);
            marksSheet.setMark_id(mId);

            Marks_SheetDAO marks_sheetDAO = new MarkSheetDAOImpl();
            marks_sheetDAO.save(marksSheet);

            resp.sendRedirect("data");

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
