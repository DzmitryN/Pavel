package dao.entities.impl;

import connector.DataSource;
import exceptions.DAOException;
import dao.interfaces.SubjectDAO;
import entities.Subject;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import utils.ClosePS;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<String, PreparedStatement> setOfPs = new HashMap<>();
    Connection connection ;
    private static final Logger logger = LogManager.getLogger(SubjectDAOImpl.class);
    static{
        BasicConfigurator.configure();
    }


    public SubjectDAOImpl() throws DAOException {
        try {
            connection = DataSource.getInstance().getConnection();
            logger.info("Connection created.");
        }catch(Exception e){
            logger.error("Exception occurred in SubjectDAOImpl constructor: ", e);
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
                setOfPs.put("selectAllInRangePS", selectAllInRangePS);
                logger.info("selectAllInRangePS created.");
                selectAllInRangePS.setInt(1, range * 10);
                rs = selectAllInRangePS.executeQuery();
            }else{
                selectAllPS = connection.prepareStatement(selectAllQuery);
                logger.info("selectAllPS created");
                setOfPs.put("selectAllPS", selectAllPS);
                rs = selectAllPS.executeQuery();
            }
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setId(rs.getInt("ID"));
                subject.setSubject(rs.getString("Subject"));
                listSubjects.add(subject);
            }
        }catch(Exception e){
            logger.error("Exception occurred in findAll function", e);
            throw new DAOException("Exception occurred in findAll function", e);
        }finally {
            try {
                rs.close();
            } catch (Exception e) {
                logger.error("Exception occurred in findAll function, finally block", e);
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
            setOfPs.put("insertPS", insertPS);
            logger.info("insertPS created");
            insertPS.setObject(1, subject.getSubject());
            insertPS.executeUpdate();
        }catch(Exception e){
            logger.error("Exception occurred in save function" , e);
            throw new DAOException("Exception occurred in save function" , e);
        }
    }

    @Override
    public Subject getById(int id) throws DAOException {
        Subject subject = new Subject();
        ResultSet rs = null;
        try{
            getByIdPS = connection.prepareStatement(getByIdQuery);
            setOfPs.put("getByIdPS", getByIdPS);
            logger.info("getByIdPS created");
            getByIdPS.setInt(1, id);
            rs = getByIdPS.executeQuery();
            while (rs.next()) {
                subject.setId(rs.getInt("ID"));
                subject.setSubject(rs.getString("SUBJECT"));
            }
        }catch(Exception e){
            logger.error("Exception occurred in getById function", e);
            throw new DAOException("Exception occurred in getById function", e);
        }
        finally {
            try {
                rs.close();
            } catch (Exception e) {
                logger.error("Exception occurred in getById function, finally block", e);
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
            setOfPs.put("updatePS", updatePS);
            logger.info("updatePS created");
            updatePS.setString(1, subject.getSubject());
            updatePS.setInt(2, subject.getId());
            updatePS.executeUpdate();
        }catch(Exception e){
            logger.error("Exception occurred in update function." , e);
            throw new DAOException("Exception occurred in update function." , e);
        }finally{
            try {
            }catch(Exception e) {
                logger.error("Exception occurred in Close function, Update PS", e);
                throw new DAOException("Exception occurred in Close function, Update PS", e);
            }
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        try
        {
            deletePS = connection.prepareStatement(deleteQuery);
            setOfPs.put("deletePS", deletePS);
            logger.info("deletePS created");
            deletePS.setInt(1, id);
            deletePS.executeUpdate();
        }catch(Exception e){
            logger.error("Exception occurred in delete function.", e);
            throw new DAOException("Exception occurred in delete function.", e);
        }
    }

    @Override
    public int pagination() throws DAOException{
        int result = 0;
        ResultSet rs = null;
        try{
            paginationPS = connection.prepareStatement(paginationQuery);
            setOfPs.put("paginationPS", paginationPS);
            logger.info("paginationPS created");
            rs =  paginationPS.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        }catch(Exception e){
            logger.error("Exception occurred in pagination call StudentDAO", e);
            throw new DAOException("Exception occurred in pagination call StudentDAO", e);
        }finally {
            try {
                rs.close();
            } catch (Exception e) {
                logger.error("Exception occurred in getById function, finally block", e);
                throw new DAOException("Exception occurred in getById function, finally block", e);
            }
        }
        return result;
    }

    public void close() throws DAOException {
        DAOException daoException;
        ClosePS closeAllPreparedStatements = new ClosePS();
        daoException = closeAllPreparedStatements.closePS(setOfPs, connection);
        if(daoException != null){
            throw new DAOException(daoException);
        }
    }
}
