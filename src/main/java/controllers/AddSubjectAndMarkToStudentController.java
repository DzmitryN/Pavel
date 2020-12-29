package controllers;


import dao.entities.impl.MarkSheetDAOImpl;
import dao.interfaces.Marks_SheetDAO;
import entities.MarksSheet;
import exceptions.DAOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddSubjectAndMarkToStudentController {
    public void OpenPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try{
            int sId=Integer.parseInt(request.getParameter("student"));
            int sbId=Integer.parseInt(request.getParameter("subject"));
            int mId=Integer.parseInt(request.getParameter("mark"));

            MarksSheet marksSheet = new MarksSheet();
            marksSheet.setStudent_id(sId);
            marksSheet.setSubject_id(sbId);
            marksSheet.setMark_id(mId);

            Marks_SheetDAO marks_sheetDAO = new MarkSheetDAOImpl();
            marks_sheetDAO.save(marksSheet);

            response.sendRedirect("data");
            marks_sheetDAO.close();

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
