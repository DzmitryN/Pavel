package dao.interfaces;


import entities.MarksSheet;
import exceptions.DAOException;

import java.util.List;

public interface Marks_SheetDAO {

    List<MarksSheet> findAll(int range) throws DAOException;
    void save(MarksSheet mark) throws DAOException;
    List<MarksSheet> getById(int id, int start) throws DAOException;
    void update(MarksSheet mark);
    void delete(int id);
    int pagination() throws DAOException;
    int paginationByStudentId(int id) throws DAOException;
    void close() throws DAOException;

}
