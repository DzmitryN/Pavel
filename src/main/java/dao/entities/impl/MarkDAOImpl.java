package dao.entities.impl;

import connector.DataSource;
import exceptions.DAOException;
import dao.interfaces.MarkDAO;
import entities.Mark;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import utils.ClosePS;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<String, PreparedStatement> setOfPs = new HashMap<>();
    Connection connection;
    private static final Logger logger = LogManager.getLogger(MarkDAOImpl.class);
    static{
        BasicConfigurator.configure();
    }


    public MarkDAOImpl() throws DAOException {
        try {
            connection = DataSource.getInstance().getConnection();
            logger.info("Connection created");
        }catch(Exception e){
            logger.error("Exception occurred in MarkDAOImpl constructor: ", e);
            throw  new DAOException("Exception occurred in MarkDAOImpl constructor: ", e);
        }
    }


    @Override
    public List<Mark> findAll() throws DAOException {
        List<Mark> marks = new ArrayList<>();
        ResultSet rs = null;
        try  {
            selectAllPS = connection.prepareStatement(selectAllQuery);
            setOfPs.put("selectAllPS", selectAllPS);
            logger.info("selectAllPS created.");
            rs = selectAllPS.executeQuery();
            while (rs.next()) {
                Mark mark = new Mark();
                mark.setID(rs.getInt("ID"));
                mark.setMark(rs.getInt("Mark"));
                marks.add(mark);
            }
        } catch (Exception e) {
            logger.error("Exception occurred in findAll function", e);
            throw new DAOException("Exception occurred in findAll function", e);
        }finally {
            try {
                rs.close();
            }catch(Exception e){
                logger.error("Exception occurred in findAll function rs close block", e);
                throw new DAOException("Exception occurred in findAll function rs close block", e);
            }
        }
        return marks;
    }


    @Override
    public void save(Mark mark) throws DAOException {
        try{
            insertPS = connection.prepareStatement(insertQuery);
            setOfPs.put("insertPS", insertPS);
            insertPS.setInt(1, mark.getMark());
            logger.info("insertPS created.");
            insertPS.executeUpdate();
        }catch (Exception e) {
            logger.error("Exception occurred in save function" , e);
             throw new DAOException("Exception occurred in save function" , e);
        }
    }

    @Override
    public Mark getById(int id) throws DAOException {
        Mark mark = new Mark();
        ResultSet rs = null;
        try{
            getByIdPS = connection.prepareStatement(getByIdQuery);
            setOfPs.put("getByIdPS", getByIdPS);
            getByIdPS.setInt(1, id);
            logger.info("getByIdPS created.");
            rs = getByIdPS.executeQuery();
            while (rs.next()) {
                mark.setID(rs.getInt("ID"));
                mark.setMark(rs.getInt("Mark"));
            }
        } catch (Exception e) {
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
        return mark;
    }

    @Override
    public void update(Mark mark) throws DAOException {
        try
        {
            updatePS = connection.prepareStatement(updateQuery);
            setOfPs.put("updatePS", updatePS);
            updatePS.setInt(1, mark.getMark());
            updatePS.setInt(2, mark.getID());
            logger.info("updatePS created.");
            updatePS.executeUpdate();
        } catch (Exception e) {
            logger.error("Exception occurred in update function." , e);
            throw new DAOException("Exception occurred in update function." , e);
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        try{
            deletePS = connection.prepareStatement(deleteQuery);
            setOfPs.put("deletePS", deletePS);
            deletePS.setInt(1, id);
            logger.info("deletePS created.");
            deletePS.executeUpdate();
        } catch (Exception e) {
            logger.error("Exception occurred in delete function.", e);
            throw new DAOException("Exception occurred in delete function.", e);
        }
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