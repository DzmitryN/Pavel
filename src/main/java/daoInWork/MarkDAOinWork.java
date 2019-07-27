package daoInWork;

import exceptions.DAOException;
import dao.interfaces.MarkDAO;
import dao.entities.impl.MarkDAOImpl;
import entities.Mark;
import java.util.ArrayList;
import java.util.List;

public class MarkDAOinWork {

    public static void main(String[] args) throws DAOException {

        MarkDAO markDAO = new MarkDAOImpl();
        try {
            List<Mark> list = new ArrayList<>();

            markDAO.save(new Mark(5));
            list.addAll(markDAO.findAll());
            markDAO.getById(1);
             markDAO.update(new Mark(4, 4));
            markDAO.delete(19);
            //list.addAll(markDAO.findAll());
            markDAO.close();
        }catch(Exception e){
            markDAO.close();
            System.out.println("Exception occurred in MarkDAOinWork: " + e.getMessage());
        }
    }
}
