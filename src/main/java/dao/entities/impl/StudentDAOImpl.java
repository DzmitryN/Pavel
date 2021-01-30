package dao.entities.impl;
import connector.DataSource;
import exceptions.DAOException;
import dao.interfaces.StudentDAO;
import entities.Student;
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

public class StudentDAOImpl /*extends AbstractDAO*/ implements StudentDAO {

    private static final String selectAllStudentsInRangeQuery = String.format("SELECT ID, FIRST_NAME, SECOND_NAME FROM " +
            "Studschema.STUDENT LIMIT ?, %d", LINES_DISPLAY_PER_PAGE);
    private PreparedStatement selectAllStudentsInRangePS;
    private static final String selectAllQuery = "SELECT ID, FIRST_NAME, SECOND_NAME FROM Studschema.STUDENT";
    private PreparedStatement selectAllPS;
    private static final String insertQuery = "INSERT INTO Studschema.STUDENT(FIRST_NAME, SECOND_NAME) VALUES (?, ?)";
    private PreparedStatement insertPS;
    private static final String getByIdQuery = "SELECT ID, FIRST_NAME, SECOND_NAME FROM Studschema.STUDENT WHERE ID = ?";
    private  PreparedStatement getByIdPS;
    private static final String updateQuery = "UPDATE Studschema.STUDENT SET FIRST_NAME = ?, SECOND_NAME = ? WHERE ID = ?";
    private PreparedStatement updatePS;
    private static final String deleteQuery = "DELETE FROM Studschema.STUDENT WHERE ID = ?";
    private PreparedStatement deletePS;
    private static final String paginationQuery = "SELECT COUNT(1) FROM Studschema.STUDENT";
    private PreparedStatement paginationPS;
    private Map<String, PreparedStatement> setOfPs = new HashMap <>();
    private Connection connection;
    private static final Logger logger = LogManager.getLogger(StudentDAOImpl.class);
    static{
        BasicConfigurator.configure();
    }


    public StudentDAOImpl() throws DAOException {
        try {
            connection = DataSource.getInstance().getConnection();
            logger.info("Connection created.");
        }catch(Exception e){
            logger.error("Exception occurred in StudentDAOImpl constructor: ", e);
            throw  new DAOException("Exception occurred in StudentDAOImpl constructor: ", e);
        }
    }

    @Override
    public List<Student> findAll(Integer range) throws DAOException {
        List<Student> listStudents = new ArrayList<>();
        ResultSet rs = null;
        try {
            if (range != null) {
            selectAllStudentsInRangePS = connection.prepareStatement(selectAllStudentsInRangeQuery);
            setOfPs.put("selectAllStudentsInRangePS", selectAllStudentsInRangePS);
            selectAllStudentsInRangePS.setInt(1, range * 10);
            logger.info("selectAllStudentsInRangePS created.");
            rs = selectAllStudentsInRangePS.executeQuery();
            }else{
            selectAllPS = connection.prepareStatement(selectAllQuery);
            logger.info("selectAllPS created");
            setOfPs.put("selectAllPS", selectAllPS);
            rs = selectAllPS.executeQuery();
            }
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("ID"));
                student.setFirstName(rs.getString("FIRST_NAME"));
                student.setSecondName(rs.getString("SECOND_NAME"));
                listStudents.add(student);
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
        return listStudents;
    }

    @Override
    public void save(Student student) throws DAOException {
        try
        {
            insertPS = connection.prepareStatement(insertQuery);
            setOfPs.put("insertPS", insertPS);
            insertPS.setString(1, student.getFirstName());
            insertPS.setString(2, student.getSecondName());
            logger.info("insertPS created.");
            insertPS.executeUpdate();
        }catch(Exception e){
            logger.error("Exception occurred in save function" , e);
            throw new DAOException("Exception occurred in save function" , e);
        }
    }

    @Override
    public Student getById(int id) throws DAOException {
        Student student = new Student();
        ResultSet rs = null;
        try{
            getByIdPS = connection.prepareStatement(getByIdQuery);
            setOfPs.put("getByIdPS", getByIdPS);
            getByIdPS.setInt(1, id);
            logger.info("getByIdPS created.");
            rs = getByIdPS.executeQuery();
            while (rs.next()) {
                student.setId(rs.getInt("ID"));
                student.setFirstName(rs.getString("FIRST_NAME"));
                student.setSecondName(rs.getString("SECOND_NAME"));
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
        return student;
    }

    @Override
    public void update(Student student) throws DAOException {
        try
        {
            updatePS = connection.prepareStatement(updateQuery);
            setOfPs.put("updatePS", updatePS);
            updatePS.setString(1, student.getFirstName());
            updatePS.setString(2, student.getSecondName());
            updatePS.setInt(3, student.getId());
            logger.info("updatePS created.");
            updatePS.executeUpdate();
        }catch(Exception e){
            logger.error("Exception occurred in update function." , e);
            throw new DAOException("Exception occurred in update function." , e);
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        try
        {
            deletePS = connection.prepareStatement(deleteQuery);
            setOfPs.put("deletePS", deletePS);
            deletePS.setInt(1, id);
            logger.info("deletePS created.");
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
            logger.info("paginationPS created.");
            rs = paginationPS.executeQuery();
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
            logger.error("Exception occurred in pagination function, finally block", e);
            throw new DAOException("Exception occurred in pagination function, finally block", e);
        }
    }
        return result;
    }

    public void close() throws DAOException {
        DAOException daoException;
        ClosePS closeAllPreparedStatements = new ClosePS();
        daoException = closeAllPreparedStatements.closePS(setOfPs,connection);
        if(daoException != null){
            throw new DAOException(daoException);
        }
    }
}
