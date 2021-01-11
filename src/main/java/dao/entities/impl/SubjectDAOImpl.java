package dao.entities.impl;


import exceptions.DAOException;
import connector.AbstractDAO;
import dao.interfaces.SubjectDAO;
import entities.Subject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAOImpl extends AbstractDAO implements SubjectDAO{

    private static final String selectAll_Range_Query = "SELECT ID, Subject FROM Studschema.SUBJECT LIMIT %d0, 10";
    private PreparedStatement selectAll_Range_PS;
    private static final String selectAllQuery = "SELECT ID, Subject FROM Studschema.SUBJECT";
    private PreparedStatement selectAll_PS;
    private static final String insertQuery = "INSERT INTO Studschema.Subject(Subject) VALUES (?)";
    private PreparedStatement insert;
    private static final String getByIdQuery = "SELECT ID, SUBJECT FROM Studschema.Subject WHERE ID = ?";
    private  PreparedStatement getById_PS;
    private static final String updateQuery = "UPDATE Studschema.Subject SET Subject = ? WHERE ID = ?";
    private PreparedStatement update_PS;
    private static final String deleteQuery = "DELETE FROM Studschema.SUBJECT WHERE ID = ?";
    private PreparedStatement delete_PS;
    private static final String paginationQuery = "SELECT COUNT(ID) FROM Studschema.SUBJECT";
    private PreparedStatement pagination_PS;

    public SubjectDAOImpl() throws DAOException {
        try {
            selectAll_PS = connection.prepareStatement(selectAllQuery);
            insert = connection.prepareStatement(insertQuery);
            getById_PS = connection.prepareStatement(getByIdQuery);
            update_PS = connection.prepareStatement(updateQuery);
            delete_PS = connection.prepareStatement(deleteQuery);
            pagination_PS = connection.prepareStatement(paginationQuery);
        } catch (SQLException e) {
            throw new DAOException("Exception occurred in MarkDaoConstructor." , e);
        }
    }

    public SubjectDAOImpl(int range) throws DAOException {
        try {
            selectAll_Range_PS = connection.prepareStatement(String.format(selectAll_Range_Query, range));
            insert = connection.prepareStatement(insertQuery);
            getById_PS = connection.prepareStatement(getByIdQuery);
            update_PS = connection.prepareStatement(updateQuery);
            delete_PS = connection.prepareStatement(deleteQuery);
            pagination_PS = connection.prepareStatement(paginationQuery);
        } catch (SQLException e) {
            throw new DAOException("Exception occurred in MarkDaoConstructor." , e);
        }
    }

    @Override
    public List<Subject> findAll(boolean swap) throws DAOException {
        List<Subject> listSubjects = new ArrayList<>();
        try(ResultSet rs = (swap) ? selectAll_Range_PS.executeQuery(): selectAll_PS.executeQuery()) {
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
            getById_PS.setInt(1, id);
            rs = getById_PS.executeQuery();
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
            update_PS.setString(1, subject.getSubject());
            update_PS.setInt(2, subject.getId());
            update_PS.executeUpdate();
        }catch(Exception e){
            throw new DAOException("Exception occurred in update function." , e);
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        try
        {
            delete_PS.setInt(1, id);
            delete_PS.executeUpdate();
        }catch(Exception e){
            throw new DAOException("Exception occurred in delete function.", e);
        }
    }

    @Override
    public int pagination() throws DAOException{
        int result = 0;
        try(ResultSet rs = pagination_PS.executeQuery()){
            if (rs.next()) {
                result = rs.getInt(1);
            }
        }catch(Exception e){
            throw new DAOException("Exception occurred in pagination call StudentDAO" + e.getMessage());
        }
        return result;
    }

    public void close() throws DAOException {
        DAOException daoException = null;
        try {
            insert.close();
        }catch(Exception e) {
            daoException = new DAOException("Exception occurred in Close function, Insert PS", e);
        }
        try {
            getById_PS.close();
        }catch (Exception e) {
            daoException = new DAOException("Exception occurred in Close function, getById PS", e);
        }
        try {
            update_PS.close();
        }catch(Exception e) {
            daoException = new DAOException("Exception occurred in Close function, Update PS", e);
        }
        try {
            delete_PS.close();
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
