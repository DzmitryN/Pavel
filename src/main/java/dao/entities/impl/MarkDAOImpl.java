package dao.entities.impl;

import connector.DataSource;
import exceptions.DAOException;
import dao.interfaces.MarkDAO;
import entities.Mark;
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
    Connection connection;


    public MarkDAOImpl() throws DAOException {
        try {
            connection = DataSource.getInstance().getConnection();
        }catch(Exception e){
            throw  new DAOException("Exception occurred in MarkDAOImpl constructor: ", e);
        }
    }


    @Override
    public List<Mark> findAll() throws DAOException {
        List<Mark> marks = new ArrayList<>();
        ResultSet rs = null;
        try  {
            selectAllPS = connection.prepareStatement(selectAllQuery);
            System.out.println("selectAllPS created.");
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
                rs.close();
            }catch(Exception e){
                throw new DAOException("Exception occurred in findAll function rs close block", e);
            }
        }
        return marks;
    }


    @Override
    public void save(Mark mark) throws DAOException {
        try{
            insertPS = connection.prepareStatement(insertQuery);
            insertPS.setInt(1, mark.getMark());
            System.out.println("insertPS created.");
            insertPS.executeUpdate();
        }catch (Exception e) {
             throw new DAOException("Exception occurred in save function" , e);
        }
    }

    @Override
    public Mark getById(int id) throws DAOException {
        Mark mark = new Mark();
        ResultSet rs = null;
        try{
            getByIdPS = connection.prepareStatement(getByIdQuery);
            getByIdPS.setInt(1, id);
            System.out.println("getByIdPS created.");
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
            updatePS = connection.prepareStatement(updateQuery);
            updatePS.setInt(1, mark.getMark());
            updatePS.setInt(2, mark.getID());
            System.out.println("updatePS created.");
            updatePS.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("Exception occurred in update function." , e);
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        try{
            deletePS = connection.prepareStatement(deleteQuery);
            deletePS.setInt(1, id);
            System.out.println("deletePS created.");
            deletePS.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("Exception occurred in delete function.", e);
        }
    }

    public void close() throws DAOException {
        DAOException daoException = null;
        if(selectAllPS != null){ try{
            selectAllPS.close();
            System.out.println("selectAllPS closed");
        }catch(Exception e) {
            daoException = new DAOException("Exception occurred in Close function, selectAllInRangePS block",e);
        }}
        if(insertPS != null) {try {
            insertPS.close();
            System.out.println("insertPS closed");
        }catch(Exception e) {
            daoException = new DAOException("Exception occurred in Close function, Insert PS block", e);
        }}
        if(getByIdPS != null) {try {
            getByIdPS.close();
            System.out.println("getByIdPS closed");
        }catch (Exception e) {
            daoException = new DAOException("Exception occurred in Close function, getByIdPS block", e);
        }}
        if(updatePS != null) {try {
            updatePS.close();
            System.out.println("updatePS closed");
        }catch(Exception e) {
            daoException = new DAOException("Exception occurred in Close function, updatePS block", e);
        }}
        if(deletePS != null){ try{
            deletePS.close();
            System.out.println("deletePS closed");
        }catch(Exception e) {
            daoException = new DAOException("Exception occurred in Close function, selectAllInRangePS block",e);
        }}
        if(connection != null){try {
            //closeConnection();
            connection.close();
            System.out.println("Connection closed");
        }catch(Exception e) {
            daoException = new DAOException("Exception occurred in Close function, closeConnection block",e);
        }}
        if(daoException != null){
            throw new DAOException(daoException);
        }
    }
}