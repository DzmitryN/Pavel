package dao.entities.impl;

import exceptions.DAOException;
import dao.interfaces.MarkDAO;
import entities.Mark;
import connector.AbstractDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarkDAOImpl extends AbstractDAO implements MarkDAO {

    private static final String selectAllQuery = "SELECT ID, Mark FROM STUDSCHEMA.MARK";
    private PreparedStatement selectAll;
    private static final String insertQuery = "INSERT INTO Studschema.Mark(Mark) VALUES (?)";
    private PreparedStatement insert;
    private static final String getByIdQuery = "SELECT ID, Mark FROM Studschema.Mark WHERE ID = ?";
    private  PreparedStatement getbyIdPS;
    private static final String updateQuery = "UPDATE Studschema.Mark SET Mark = ? WHERE ID = ?";
    private PreparedStatement updatePS;
    private static final String deleteQuery = "DELETE FROM Studschema.Mark WHERE ID = ?";
    private PreparedStatement deletePS;

    public MarkDAOImpl() throws DAOException {
        try {
            selectAll = connection.prepareStatement(selectAllQuery);
            insert = connection.prepareStatement(insertQuery);
            getbyIdPS = connection.prepareStatement(getByIdQuery);
            updatePS = connection.prepareStatement(updateQuery);
            deletePS = connection.prepareStatement(deleteQuery);
        } catch (SQLException e) {
            throw new DAOException("Exception occurred in MarkDaoConstructor." , e);
        }
    }


    @Override
    public List<Mark> findAll() throws DAOException {
        List<Mark> marks = new ArrayList<>();
        try  (ResultSet rs = selectAll.executeQuery()){
            while (rs.next()) {
                Mark mark = new Mark();
                mark.setID(rs.getInt("ID"));
                mark.setMark(rs.getInt("Mark"));
                marks.add(mark);
            }
        } catch (Exception e) {
            throw new DAOException("Exception occurred in findAll function", e);
        }
        return marks;
    }


    @Override
    public void save(Mark mark) throws DAOException {
        try{
             insert.setInt(1, mark.getMark());
             insert.executeUpdate();
        }catch (Exception e) {
             throw new DAOException("Exception occurred in save function" , e);
        }
    }

    @Override
    public Mark getById(int id) throws DAOException {
        Mark mark = new Mark();
        ResultSet rs = null;
        try{
            getbyIdPS.setInt(1, id);
            rs = getbyIdPS.executeQuery();
            while (rs.next()) {
                mark.setID(rs.getInt("ID"));
                mark.setMark(rs.getInt("Mark"));
            }
        } catch (Exception e) {
            throw new DAOException("Exception occurred in getById function", e);
        }
        finally {
            try {
                    rs.close();
                } catch (Exception e) {
                    throw new DAOException("Exception occurred in getById function, finally block", e);
                }
            }
        return mark;
    }

    @Override
    public void update(Mark mark) throws DAOException {
        try
        {
            updatePS.setInt(1, mark.getMark());
            updatePS.setInt(2, mark.getID());
            updatePS.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("Exception occurred in update function." , e);
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        try{
            deletePS.setInt(1, id);
            deletePS.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("Exception occurred in delete function.", e);
        }
    }

    public void close() throws DAOException {
        DAOException daoException = null;
        try {
            insert.close();
        }catch(Exception e) {
            daoException = new DAOException("Exception occurred in Close function, Insert PS", e);
        }
        try {
            getbyIdPS.close();
        }catch (Exception e) {
            daoException = new DAOException("Exception occurred in Close function, getById PS", e);
        }
        try {
            updatePS.close();
        }catch(Exception e) {
            daoException = new DAOException("Exception occurred in Close function, Update PS", e);
        }
        try {
            deletePS.close();
        }catch(Exception e) {
            daoException = new DAOException("Exception occurred in Close function, Delete PS",e);
        }
        try {
            closeConnection();
        }catch(Exception e) {
            daoException = new DAOException("Exception occurred in Close function, closeConnection block",e);
        }
        if(daoException != null){
            throw new DAOException(daoException);
        }
    }
}