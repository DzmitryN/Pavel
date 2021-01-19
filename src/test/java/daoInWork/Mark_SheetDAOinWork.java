package daoInWork;


import exceptions.DAOException;
import dao.interfaces.Marks_SheetDAO;
import dao.entities.impl.MarkSheetDAOImpl;
import entities.MarksSheet;
import java.util.ArrayList;
import java.util.List;

import static utils.Constants.LINES_DISPLAY_PER_PAGE;

public class Mark_SheetDAOinWork {
    public static void main(String[] args) throws DAOException {
        Marks_SheetDAO marks_sheetDAO = new MarkSheetDAOImpl();

        try {
            List<MarksSheet> list = new ArrayList<>();
            list.addAll(marks_sheetDAO.findAll(0));
            marks_sheetDAO.getById(4, LINES_DISPLAY_PER_PAGE);
            marks_sheetDAO.close();
        }catch(Exception e){
            marks_sheetDAO.close();
            System.out.println("Exception occurred in Mark_SheetDAOinWork: " + e.getMessage());
        }

    }
}
