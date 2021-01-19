package dao.interfaces;


import entities.Student;
import exceptions.DAOException;

import java.util.List;

public interface StudentDAO {
    List<Student> findAll(Integer range) throws DAOException;
    void save(Student student) throws DAOException;
    Student getById(int id) throws DAOException;
    void update(Student student) throws DAOException;
    void delete(int id) throws DAOException;
    int pagination() throws DAOException;
    void close() throws DAOException;
}
