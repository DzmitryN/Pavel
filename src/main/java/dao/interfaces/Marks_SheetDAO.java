package dao.interfaces;


import entities.MarksSheet;
import exceptions.DAOException;

import java.util.List;

public interface Marks_SheetDAO {

    List<MarksSheet> findAll() throws DAOException;
    void save(MarksSheet mark) throws DAOException;
    List<MarksSheet> getById(int id) throws DAOException;
    void update(MarksSheet mark);
    void delete(int id);
    void close() throws DAOException;

}
