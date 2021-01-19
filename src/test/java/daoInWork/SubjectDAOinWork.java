package daoInWork;

import exceptions.DAOException;
import dao.interfaces.SubjectDAO;
import dao.entities.impl.SubjectDAOImpl;
import entities.Subject;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAOinWork {
    public static void main(String[] args) throws DAOException {
        SubjectDAO subjectDAO = new SubjectDAOImpl();
        try {
            List<Subject> list = new ArrayList<>();

            //subjectDAO.save(new Subject("Math"));
            //list.addAll(subjectDAO.findAll());
            //subjectDAO.getById(1);
            //subjectDAO.update(new Subject(4, "physics"));
            subjectDAO.delete(8);

            //subjectDAO.getById(1);
            list.addAll(subjectDAO.findAll(null));
            subjectDAO.close();
        }catch(Exception e){
            subjectDAO.close();
            System.out.println("Exception occurred in SubjectDAOinWork: " + e.getMessage());
        }
    }
}
