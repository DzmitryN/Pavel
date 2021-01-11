package controllers;


import dao.entities.impl.MarkSheetDAOImpl;
import dao.interfaces.Marks_SheetDAO;
import entities.MarksSheet;
import exceptions.DAOException;
import utils.Validator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReturnAllDataController {
    Validator validator = new Validator();
    public void OpenPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int start =0;
        try {
            try{
                start = Integer.parseInt(request.getParameter("start"));
            }catch(Exception e){}
            Marks_SheetDAO marks_sheetDAO = new MarkSheetDAOImpl(start);
            List<MarksSheet> list = new ArrayList<>();
            list.addAll(marks_sheetDAO.findAll());
            request.setAttribute("marks_sheetDTO", list);
            String pages = "" + validator.GetNumberOfPages(marks_sheetDAO.pagination());
            request.setAttribute("pages", pages);
            marks_sheetDAO.close();
        } catch (DAOException e) {
            e.printStackTrace();
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/Views/ReturnAllData.jsp");
        requestDispatcher.forward(request, response);
    }
}
