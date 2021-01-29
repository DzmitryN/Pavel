package dao.entities.impl;

import connector.DataSource;
import exceptions.DAOException;
import dao.interfaces.SubjectDAO;
import entities.Subject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static utils.Constants.LINES_DISPLAY_PER_PAGE;

public class SubjectDAOImpl /*extends AbstractDAO*/ implements SubjectDAO{

    private static final String selectAllInRangeQuery = String.format("SELECT ID, Subject FROM Studschema.SUBJECT " +
            "LIMIT ?, %d", LINES_DISPLAY_PER_PAGE);
    private PreparedStatement selectAllInRangePS;
    private static final String selectAllQuery = "SELECT ID, Subject FROM Studschema.SUBJECT";
    private PreparedStatement selectAllPS;
    private static final String insertQuery = "INSERT INTO Studschema.Subject(Subject) VALUES (?)";
    private PreparedStatement insertPS;
    private static final String getByIdQuery = "SELECT ID, SUBJECT FROM Studschema.Subject WHERE ID = ?";
    private  PreparedStatement getByIdPS;
    private static final String updateQuery = "UPDATE Studschema.Subject SET Subject = ? WHERE ID = ?";
    private PreparedStatement updatePS;
    private static final String deleteQuery = "DELETE FROM Studschema.SUBJECT WHERE ID = ?";
    private PreparedStatement deletePS;
    private static final String paginationQuery = "SELECT COUNT(ID) FROM Studschema.SUBJECT";
    private PreparedStatement paginationPS;
    Connection connection;


    public SubjectDAOImpl() throws DAOException {
        try {
            connection = DataSource.getInstance().getConnection();
            System.out.println("Connection created.");
        }catch(Exception e){
            throw  new DAOException("Exception occurred in SubjectDAOImpl constructor: ", e);
        }
    }

    @Override
    public List<Subject> findAll(Integer range) throws DAOException {
        List<Subject> listSubjects = new ArrayList<>();
        ResultSet rs = null;
        try{
            if (range != null) {
                selectAllInRangePS = connection.prepareStatement(selectAllInRangeQuery);
                System.out.println("selectAllInRangePS created.");
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
            insertPS = connection.prepareStatement(insertQuery);
            System.out.println("insertPS created");
            insertPS.setObject(1, subject.getSubject());
            insertPS.executeUpdate();
        }catch(Exception e){
            throw new DAOException("Exception occurred in save function" , e);
        }
    }

    @Override
    public Subject getById(int id) throws DAOException {
        Subject subject = new Subject();
        ResultSet rs = null;
        try{
            getByIdPS = connection.prepareStatement(getByIdQuery);
            System.out.println("getByIdPS created");
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
            System.out.println("updatePS created");
            updatePS.setString(1, subject.getSubject());
            updatePS.setInt(2, subject.getId());
            updatePS.executeUpdate();
        }catch(Exception e){
            throw new DAOException("Exception occurred in update function." , e);
        }finally{
            try {
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
            System.out.println("deletePS created");
            deletePS.setInt(1, id);
            deletePS.executeUpdate();
        }catch(Exception e){
            throw new DAOException("Exception occurred in delete function.", e);
        }
    }

    @Override
    public int pagination() throws DAOException{
        int result = 0;
        ResultSet rs = null;
        try{
            paginationPS = connection.prepareStatement(paginationQuery);
            System.out.println("paginationPS created");
            rs =  paginationPS.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        }catch(Exception e){
            throw new DAOException("Exception occurred in pagination call StudentDAO" + e.getMessage());
        }finally {
            try {
                rs.close();
            } catch (Exception e) {
                throw new DAOException("Exception occurred in getById function, finally block", e);
            }
        }
        return result;
    }

    public void close() throws DAOException {
        DAOException daoException = null;
        if(selectAllInRangePS != null){ try{
            selectAllInRangePS.close();
            System.out.println("selectAllInRangePS closed");
        }catch(Exception e) {
            daoException = new DAOException("Exception occurred in Close function, selectAllInRangePS block",e);
        }}
        if(selectAllPS != null){ try{
            selectAllPS.close();
            System.out.println("selectAllPS closed");
        }catch(Exception e) {
            daoException = new DAOException("Exception occurred in Close function, selectAllPS block",e);
        }}
        if(insertPS != null){ try{
            insertPS.close();
            System.out.println("insertPS closed");
        }catch(Exception e) {
            daoException = new DAOException("Exception occurred in Close function, insertPS block",e);
        }}
        if(getByIdPS != null){ try{
            getByIdPS.close();
            System.out.println("getByIdPS closed");
        }catch(Exception e) {
            daoException = new DAOException("Exception occurred in Close function, getByIdPS block",e);
        }}
        if(updatePS != null){ try{
            updatePS.close();
            System.out.println("updatePS closed");
        }catch(Exception e) {
            daoException = new DAOException("Exception occurred in Close function, updatePS block",e);
        }}
        if(deletePS != null){ try{
            deletePS.close();
            System.out.println("deletePS closed");
        }catch(Exception e) {
            daoException = new DAOException("Exception occurred in Close function, deletePS block",e);
        }}
        if(paginationPS != null){ try{
            paginationPS.close();
            System.out.println("paginationPS closed");
        }catch(Exception e) {
            daoException = new DAOException("Exception occurred in Close function, paginationPS block",e);
        }}
        if(connection != null){ try {
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
