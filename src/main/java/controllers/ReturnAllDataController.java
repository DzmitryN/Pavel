package controllers;


import dao.entities.impl.MarkSheetDAOImpl;
import dao.interfaces.Marks_SheetDAO;
import entities.MarksSheet;
import exceptions.DAOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReturnAllDataController {
    public void OpenPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


        try {
            Marks_SheetDAO marks_sheetDAO = new MarkSheetDAOImpl();
            List<MarksSheet> list = new ArrayList<>();
            list.addAll(marks_sheetDAO.findAll());
            request.setAttribute("marks_sheetDTO", list);
            marks_sheetDAO.close();
        } catch (DAOException e) {
            e.printStackTrace();
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/Views/ReturnAllData.jsp");
        requestDispatcher.forward(request, response);
    }
}
