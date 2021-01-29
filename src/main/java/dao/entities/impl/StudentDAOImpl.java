package dao.entities.impl;
import connector.DataSource;
import exceptions.DAOException;
import dao.interfaces.StudentDAO;
import entities.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    Connection connection;


    public StudentDAOImpl() throws DAOException {
        try {
            connection = DataSource.getInstance().getConnection();
            System.out.println("Connection created.");
        }catch(Exception e){
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
            selectAllStudentsInRangePS.setInt(1, range * 10);
            System.out.println("selectAllStudentsInRangePS created.");
            rs = selectAllStudentsInRangePS.executeQuery();
            }else{
            selectAllPS = connection.prepareStatement(selectAllQuery);
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
            throw new DAOException("Exception occurred in findAll function", e);
        }finally {
            try {
                rs.close();
            } catch (Exception e) {
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
            insertPS.setString(1, student.getFirstName());
            insertPS.setString(2, student.getSecondName());
            System.out.println("insertPS created.");
            insertPS.executeUpdate();
        }catch(Exception e){
            throw new DAOException("Exception occurred in save function" , e);
        }
    }

    @Override
    public Student getById(int id) throws DAOException {
        Student student = new Student();
        ResultSet rs = null;
        try{
            getByIdPS = connection.prepareStatement(getByIdQuery);
            getByIdPS.setInt(1, id);
            System.out.println("getByIdPS created.");
            rs = getByIdPS.executeQuery();
            while (rs.next()) {
                student.setId(rs.getInt("ID"));
                student.setFirstName(rs.getString("FIRST_NAME"));
                student.setSecondName(rs.getString("SECOND_NAME"));
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
        return student;
    }

    @Override
    public void update(Student student) throws DAOException {
        try
        {
            updatePS = connection.prepareStatement(updateQuery);
            updatePS.setString(1, student.getFirstName());
            updatePS.setString(2, student.getSecondName());
            updatePS.setInt(3, student.getId());
            System.out.println("updatePS created.");
            updatePS.executeUpdate();
        }catch(Exception e){
            throw new DAOException("Exception occurred in update function." , e);
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        try
        {
            deletePS = connection.prepareStatement(deleteQuery);
            deletePS.setInt(1, id);
            System.out.println("deletePS created.");
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
            System.out.println("paginationPS created.");
            rs = paginationPS.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        }catch(Exception e){
            throw new DAOException("Exception occurred in pagination call StudentDAO" + e.getMessage());
        }finally {
        try {
            rs.close();
        } catch (Exception e) {
            throw new DAOException("Exception occurred in pagination function, finally block", e);
        }
    }
        return result;
    }

    public void close() throws DAOException {
        DAOException daoException = null;
        if(selectAllStudentsInRangePS != null){ try{
            selectAllStudentsInRangePS.close();
            System.out.println("selectAllStudentsInRangePS closed");
        }catch(Exception e) {
            daoException = new DAOException("Exception occurred in Close function, selectAllStudentsInRangePS block",e);
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
        }catch(Exception e) {
            daoException = new DAOException("Exception occurred in Close function, closeConnection block",e);
        }}
        if(daoException != null){
            throw new DAOException(daoException);
        }
    }
}
