package controllers;


import dao.entities.impl.MarkSheetDAOImpl;
import dao.interfaces.Marks_SheetDAO;
import entities.MarksSheet;
import exceptions.DAOException;
import utils.Paginator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReturnAllDataController {
    Paginator paginator = new Paginator();
    public void openPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int start =0;
        try {
            try{
                start = Integer.parseInt(request.getParameter("start"));
            }catch(Exception e){}
            Marks_SheetDAO marks_sheetDAO = new MarkSheetDAOImpl();
            List<MarksSheet> list = new ArrayList<>();
            list.addAll(marks_sheetDAO.findAll(start));
            request.setAttribute("marks_sheetDTO", list);
            String pages = "" + paginator.getNumberOfPages(marks_sheetDAO.pagination());
            request.setAttribute("pages", pages);
            marks_sheetDAO.close();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/WEB-INF/Views/ReturnAllData.jsp").forward(request, response);
    }
}
