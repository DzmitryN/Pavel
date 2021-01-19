package dao.entities.impl;


import exceptions.DAOException;
import connector.AbstractDAO;
import dao.interfaces.SubjectDAO;
import entities.Subject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static utils.Constants.LINES_DISPLAY_PER_PAGE;

public class SubjectDAOImpl extends AbstractDAO implements SubjectDAO{

    private static final String selectAllInRangeQuery = String.format("SELECT ID, Subject FROM Studschema.SUBJECT " +
            "LIMIT ?, %d", LINES_DISPLAY_PER_PAGE);
    private PreparedStatement selectAllInRangePS;
    private static final String selectAllQuery = "SELECT ID, Subject FROM Studschema.SUBJECT";
    private PreparedStatement selectAllPS;
    private static final String insertQuery = "INSERT INTO Studschema.Subject(Subject) VALUES (?)";
    private PreparedStatement insert;
    private static final String getByIdQuery = "SELECT ID, SUBJECT FROM Studschema.Subject WHERE ID = ?";
    private  PreparedStatement getByIdPS;
    private static final String updateQuery = "UPDATE Studschema.Subject SET Subject = ? WHERE ID = ?";
    private PreparedStatement updatePS;
    private static final String deleteQuery = "DELETE FROM Studschema.SUBJECT WHERE ID = ?";
    private PreparedStatement deletePS;
    private static final String paginationQuery = "SELECT COUNT(ID) FROM Studschema.SUBJECT";
    private PreparedStatement paginationPS;


    public SubjectDAOImpl() throws DAOException {}

    @Override
    public List<Subject> findAll(Integer range) throws DAOException {
        List<Subject> listSubjects = new ArrayList<>();
        ResultSet rs = null;
        try{
            if (range != null) {
                selectAllInRangePS = connection.prepareStatement(selectAllInRangeQuery);
                selectAllInRangePS.setInt(1, range * 10);
                rs = selectAllInRangePS.executeQuery();
            }else{
                selectAllPS = connection.prepareStatement(selectAllQuery);
                rs = selectAllPS.executeQuery();
            }
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setId(rs.getInt("ID"));
                subject.setSubject(rs.getString("Subject"));
                listSubjects.add(subject);
            }
        }catch(Exception e){
            throw new DAOException("Exception occurred in findAll function", e);
        }finally {
            try {
                if(selectAllInRangePS != null) selectAllInRangePS.close();
                if(selectAllPS != null) selectAllPS.close();
                rs.close();
            } catch (Exception e) {
                throw new DAOException("Exception occurred in findAll function, finally block", e);
            }
        }
        return listSubjects;
    }

    @Override
    public void save(Subject subject) throws DAOException {
        try
        {
            insert = connection.prepareStatement(insertQuery);
            insert.setObject(1, subject.getSubject());
            insert.executeUpdate();
        }catch(Exception e){
            throw new DAOException("Exception occurred in save function" , e);
        }finally{
            try {
                insert.close();
            }catch(Exception e) {
                throw new DAOException("Exception occurred in Close function, Insert PS", e);
            }
        }
    }

    @Override
    public Subject getById(int id) throws DAOException {
        Subject subject = new Subject();
        ResultSet rs = null;
        try{
            getByIdPS = connection.prepareStatement(getByIdQuery);
            getByIdPS.setInt(1, id);
            rs = getByIdPS.executeQuery();
            while (rs.next()) {
                subject.setId(rs.getInt("ID"));
                subject.setSubject(rs.getString("SUBJECT"));
            }
        }catch(Exception e){
            throw new DAOException("Exception occurred in getById function", e);
        }
        finally {
            try {
                getByIdPS.close();
                rs.close();
            } catch (Exception e) {
                throw new DAOException("Exception occurred in getById function, finally block", e);
            }
        }
        return subject;
    }

    @Override
    public void update(Subject subject) throws DAOException {
        try
        {
            updatePS = connection.prepareStatement(updateQuery);
            updatePS.setString(1, subject.getSubject());
            updatePS.setInt(2, subject.getId());
            updatePS.executeUpdate();
        }catch(Exception e){
            throw new DAOException("Exception occurred in update function." , e);
        }finally{
            try {
                updatePS.close();
            }catch(Exception e) {
                throw new DAOException("Exception occurred in Close function, Update PS", e);
            }
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        try
        {
            deletePS = connection.prepareStatement(deleteQuery);
            deletePS.setInt(1, id);
            deletePS.executeUpdate();
        }catch(Exception e){
            throw new DAOException("Exception occurred in delete function.", e);
        }finally{
            try {
                deletePS.close();
            }catch(Exception e) {
                throw new DAOException("Exception occurred in Close function, Delete PS",e);
            }
        }
    }

    @Override
    public int pagination() throws DAOException{
        int result = 0;
        ResultSet rs = null;
        try{
            paginationPS = connection.prepareStatement(paginationQuery);
            rs =  paginationPS.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        }catch(Exception e){
            throw new DAOException("Exception occurred in pagination call StudentDAO" + e.getMessage());
        }finally {
            try {
                paginationPS.close();
                rs.close();
            } catch (Exception e) {
                throw new DAOException("Exception occurred in getById function, finally block", e);
            }
        }
        return result;
    }

    public void close() throws DAOException {
        DAOException daoException = null;
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
