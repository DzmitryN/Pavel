package com.servletSample.servlet;

import dao.entities.impl.MarkSheetDAOImpl;
import dao.interfaces.Marks_SheetDAO;
import entities.MarksSheet;
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

//@WebServlet("/data")
public class ReturnAllDataServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException, IOException {

        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();
        out.println("<h1>Show All Sheet</h1>");

        out.print("<table border='1' width='100%'");
        out.print("<tr><th>First Name</th><th>Second Name</th><th>Subject</th><th>Mark</th></tr>");

        try {
            Marks_SheetDAO marks_sheetDAO = new MarkSheetDAOImpl();
            List<MarksSheet> list = new ArrayList<>();
            list.addAll(marks_sheetDAO.findAll());
            for (MarksSheet markSheet : list){
                out.print("<tr><td>"+markSheet.getFirstName()+"</td><td>"+markSheet.getSecondName()+"</td>" +
                        "<td>"+markSheet.getSubject()+"</td><td>"+markSheet.getMark()+"</td></tr>");
            }
            marks_sheetDAO.close();
        } catch (DAOException e) {
            e.printStackTrace();
        }

        out.print("</table>");

        out.write("<p>");
        out.write("<a href=\"returnDataForStudent\"><input type= \"button\" value= \"Get All Data for the Student\"></a>");
        out.write("</p>");

        out.write("<p>");
        out.write("<a href=\"addNewDataToStudent\"><input type= \"button\" value= \"Add new subject and mark to student\"></a>");
        out.write("</p>");


        out.write("<p>");
        out.write("<a href=\"/\"><input type= \"button\" value= \"Press for return to start page\"></a>");
        out.write("</p>");
        out.close();
    }

    @Override

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException, IOException {

        doGet(req, resp);

    }
}
