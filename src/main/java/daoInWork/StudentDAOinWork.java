package daoInWork;


import dao.interfaces.StudentDAO;
import dao.entities.impl.StudentDAOImpl;
import entities.Student;
import exceptions.DAOException;

import java.util.ArrayList;
import java.util.List;

public class StudentDAOinWork {
    public static void main(String[] args) throws DAOException {
        StudentDAO studentDao = null;
        try {
            studentDao = new StudentDAOImpl();
            Student student = new Student();
            List<Student> list = new ArrayList<>();

            studentDao.save(student.studentInitializing("Taymoor1", "Heroev2"));
            list.addAll(studentDao.findAll());
            studentDao.getById(1);
            studentDao.update(student.studentInitializing(1, "Stepan", "Makarov"));
            //studentDao.delete(7);
            //list.addAll(studentDao.findAll());
            studentDao.close();
        }catch(Exception e){
             studentDao.close();
             System.out.println("Exception occurred in StudentDAOinWork: " + e.getMessage());

        }
    }
}
