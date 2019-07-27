package dao.interfaces;
import entities.Mark;
import exceptions.DAOException;

import java.util.List;

public interface MarkDAO {
    List<Mark> findAll() throws DAOException;
    void save(Mark mark) throws DAOException;
    Mark getById(int id) throws DAOException;
    void update(Mark mark) throws DAOException;
    void delete(int id) throws DAOException;
    void close() throws DAOException;
}
