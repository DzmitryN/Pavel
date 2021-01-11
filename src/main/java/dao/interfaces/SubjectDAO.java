package dao.interfaces;


import entities.Subject;
import exceptions.DAOException;

import java.util.List;

public interface SubjectDAO {
    List<Subject> findAll(boolean swap) throws DAOException;
    void save(Subject subject) throws DAOException;
    Subject getById(int id) throws DAOException;
    void update(Subject subject) throws DAOException;
    void delete(int id) throws DAOException;
    int pagination() throws DAOException;
    void close() throws DAOException;
}
