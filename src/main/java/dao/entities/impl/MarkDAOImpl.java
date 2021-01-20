package dao.entities.impl;

import connector.ConnectionPool;
import exceptions.DAOException;
import dao.interfaces.MarkDAO;
import entities.Mark;
import connector.AbstractDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarkDAOImpl /*extends AbstractDAO*/ implements MarkDAO {

    private static final String selectAllQuery = "SELECT ID, Mark FROM STUDSCHEMA.MARK";
    private PreparedStatement selectAllPS;
    private static final String insertQuery = "INSERT INTO Studschema.Mark(Mark) VALUES (?)";
    private PreparedStatement insertPS;
    private static final String getByIdQuery = "SELECT ID, Mark FROM Studschema.Mark WHERE ID = ?";
    private  PreparedStatement getByIdPS;
    private static final String updateQuery = "UPDATE Studschema.Mark SET Mark = ? WHERE ID = ?";
    private PreparedStatement updatePS;
    private static final String deleteQuery = "DELETE FROM Studschema.Mark WHERE ID = ?";
    private PreparedStatement deletePS;

    public MarkDAOImpl() throws DAOException {}


    @Override
    public List<Mark> findAll() throws DAOException {
        List<Mark> marks = new ArrayList<>();
        ResultSet rs = null;
        Connection connection = null;
        try  {
            System.out.println("MarkDAO findAll, number of free connections: " + ConnectionPool.jdbcConnectionPool.getActiveConnections());
            connection = ConnectionPool.jdbcConnectionPool.getConnection();
            selectAllPS = connection.prepareStatement(selectAllQuery);
            rs = selectAllPS.executeQuery();
            while (rs.next()) {
                Mark mark = new Mark();
                mark.setID(rs.getInt("ID"));
                mark.setMark(rs.getInt("Mark"));
                marks.add(mark);
            }
        } catch (Exception e) {
            throw new DAOException("Exception occurred in findAll function", e);
        }finally {
            try {
                selectAllPS.close();
                rs.close();
                if(connection != null) connection.close();
            }catch(Exception e){

            }

        }
        return marks;
    }


    @Override
    public void save(Mark mark) throws DAOException {
        Connection connection = null;
        try{
            System.out.println("MarkDAO save, number of free connections: " + ConnectionPool.jdbcConnectionPool.getActiveConnections());
            connection = ConnectionPool.jdbcConnectionPool.getConnection();
            insertPS = connection.prepareStatement(insertQuery);
            insertPS.setInt(1, mark.getMark());
            insertPS.executeUpdate();
        }catch (Exception e) {
             throw new DAOException("Exception occurred in save function" , e);
        }finally{
            try {
                selectAllPS.close();
            if (connection != null) connection.close();
        }catch(Exception ex){

        }

        }
    }

    @Override
    public Mark getById(int id) throws DAOException {
        Mark mark = new Mark();
        ResultSet rs = null;
        Connection connection = null;
        try{
            System.out.println("MarkDAO get by id, number of free connections: " + ConnectionPool.jdbcConnectionPool.getActiveConnections());
            connection = ConnectionPool.jdbcConnectionPool.getConnection();
            getByIdPS = connection.prepareStatement(getByIdQuery);
            getByIdPS.setInt(1, id);
            rs = getByIdPS.executeQuery();
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
                getByIdPS.close();
                if(connection != null) connection.close();
            } catch (Exception e) {
                    throw new DAOException("Exception occurred in getById function, finally block", e);
                }
            }
        return mark;
    }

    @Override
    public void update(Mark mark) throws DAOException {
        Connection connection = null;
        try
        {
            System.out.println("MarkDAO update, number of free connections: " + ConnectionPool.jdbcConnectionPool.getActiveConnections());
            connection = ConnectionPool.jdbcConnectionPool.getConnection();
            updatePS = connection.prepareStatement(updateQuery);
            updatePS.setInt(1, mark.getMark());
            updatePS.setInt(2, mark.getID());
            updatePS.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("Exception occurred in update function." , e);
        }finally {
            try {
                updatePS.close();
                if(connection != null) connection.close();
            } catch (Exception e) {
                throw new DAOException("Exception occurred in getById function, finally block", e);
            }
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        Connection connection = null;
        try{
            System.out.println("MarkDAO delete, number of free connections: " + ConnectionPool.jdbcConnectionPool.getActiveConnections());
            connection = ConnectionPool.jdbcConnectionPool.getConnection();
            deletePS = connection.prepareStatement(deleteQuery);
            deletePS.setInt(1, id);
            deletePS.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("Exception occurred in delete function.", e);
        }finally{
            try {
                deletePS.close();
                if(connection != null) connection.close();
            }catch(Exception e) {
                throw new DAOException("Exception occurred in Close block for DeletePS",e);
            }
        }
    }

    public void close() throws DAOException {
        DAOException daoException = null;
        try {
            insertPS.close();
        }catch(Exception e) {
            daoException = new DAOException("Exception occurred in Close function, Insert PS", e);
        }
        try {
            getByIdPS.close();
        }catch (Exception e) {
            daoException = new DAOException("Exception occurred in Close function, getById PS", e);
        }
        try {
            updatePS.close();
        }catch(Exception e) {
            daoException = new DAOException("Exception occurred in Close function, Update PS", e);
        }

        try {
            //closeConnection();
            //connection.close();
        }catch(Exception e) {
            daoException = new DAOException("Exception occurred in Close function, closeConnection block",e);
        }
        if(daoException != null){
            throw new DAOException(daoException);
        }
    }
}