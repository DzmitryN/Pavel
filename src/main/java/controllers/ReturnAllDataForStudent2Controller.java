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

public class ReturnAllDataForStudent2Controller {
    Paginator paginator = new Paginator();
    public void openPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int start =0;
        try{

            try{
                start = Integer.parseInt(request.getParameter("start"));
            }catch(Exception e){}
            int sId = 0;
            try{
                sId = Integer.parseInt(request.getParameter("student"));
            }catch(Exception e){
            }

            Marks_SheetDAO marks_sheetDAO = new MarkSheetDAOImpl();
            List<MarksSheet> list = new ArrayList<>();
            list.addAll(marks_sheetDAO.getById(sId, start));
            request.setAttribute("MS_DTO", list);
            String pages = "" + paginator.getNumberOfPages(marks_sheetDAO.pagination_ById(sId));
            request.setAttribute("pages", pages);
            request.setAttribute("StudentId", "" + sId);
            marks_sheetDAO.close();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/WEB-INF/Views/ReturnAllDataForStudent2.jsp").forward(request, response);
    }
}
