package dao.entities.impl;


import exceptions.DAOException;
import connector.AbstractDAO;
import dao.interfaces.SubjectDAO;
import entities.Subject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAOImpl extends AbstractDAO implements SubjectDAO{

    private static final String selectAllQuery = "SELECT ID, Subject FROM Studschema.SUBJECT";
    private PreparedStatement selectAll;
    private static final String insertQuery = "INSERT INTO Studschema.Subject(Subject) VALUES (?)";
    private PreparedStatement insert;
    private static final String getByIdQuery = "SELECT ID, SUBJECT FROM Studschema.Subject WHERE ID = ?";
    private  PreparedStatement getbyIdPS;
    private static final String updateQuery = "UPDATE Studschema.Subject SET Subject = ? WHERE ID = ?";
    private PreparedStatement updatePS;
    private static final String deleteQuery = "DELETE FROM Studschema.SUBJECT WHERE ID = ?";
    private PreparedStatement deletePS;

    public SubjectDAOImpl() throws DAOException {
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
    public List<Subject> findAll() throws DAOException {
        List<Subject> listSubjects = new ArrayList<>();
        try(ResultSet rs = selectAll.executeQuery()) {
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setId(rs.getInt("ID"));
                subject.setSubject(rs.getString("Subject"));
                listSubjects.add(subject);
            }
        }catch(Exception e){
            throw new DAOException("Exception occurred in findAll function", e);
        }
        return listSubjects;
    }

    @Override
    public void save(Subject subject) throws DAOException {
        try
        {
            insert.setObject(1, subject.getSubject());
            insert.executeUpdate();
        }catch(Exception e){
            throw new DAOException("Exception occurred in save function" , e);
        }
    }

    @Override
    public Subject getById(int id) throws DAOException {
        Subject subject = new Subject();
        ResultSet rs = null;
        try{
            getbyIdPS.setInt(1, id);
            rs = getbyIdPS.executeQuery();
            while (rs.next()) {
                subject.setId(rs.getInt("ID"));
                subject.setSubject(rs.getString("SUBJECT"));
            }
        }catch(Exception e){
            throw new DAOException("Exception occurred in getById function", e);
        }
        finally {
            try {
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
            updatePS.setString(1, subject.getSubject());
            updatePS.setInt(2, subject.getId());
            updatePS.executeUpdate();
        }catch(Exception e){
            throw new DAOException("Exception occurred in update function." , e);
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        try
        {
            deletePS.setInt(1, id);
            deletePS.executeUpdate();
        }catch(Exception e){
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
