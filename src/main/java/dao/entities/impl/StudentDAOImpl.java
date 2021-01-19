package dao.entities.impl;
import exceptions.DAOException;
import connector.AbstractDAO;
import dao.interfaces.StudentDAO;
import entities.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static utils.Constants.LINES_DISPLAY_PER_PAGE;

public class StudentDAOImpl extends AbstractDAO implements StudentDAO {

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


    public StudentDAOImpl() throws DAOException {}

    @Override
    public List<Student> findAll(Integer range) throws DAOException {
        List<Student> listStudents = new ArrayList<>();
        ResultSet rs = null;
        try {
            if (range != null) {
            selectAllStudentsInRangePS = connection.prepareStatement(selectAllStudentsInRangeQuery);
            selectAllStudentsInRangePS.setInt(1, range * 10);
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
                if(selectAllStudentsInRangePS != null) selectAllStudentsInRangePS.close();
                if(selectAllPS != null) selectAllPS.close();
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
            insertPS.executeUpdate();
        }catch(Exception e){
            throw new DAOException("Exception occurred in save function" , e);
        }finally {
            try {
                insertPS.close();
            } catch (Exception e) {
                throw new DAOException("Exception occurred in save function, finally block", e);
            }
        }
    }

    @Override
    public Student getById(int id) throws DAOException {
        Student student = new Student();
        ResultSet rs = null;
        try{
            getByIdPS = connection.prepareStatement(getByIdQuery);
            getByIdPS.setInt(1, id);
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
                getByIdPS.close();
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
            updatePS.executeUpdate();
        }catch(Exception e){
            throw new DAOException("Exception occurred in update function." , e);
        }finally {
            try {
                updatePS.close();
            } catch (Exception e) {
                throw new DAOException("Exception occurred in update function, finally block", e);
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
        }finally {
            try {
                deletePS.close();
            } catch (Exception e) {
                throw new DAOException("Exception occurred in delete function, finally block", e);
            }
        }
    }

    @Override
    public int pagination() throws DAOException{
        int result = 0;
        ResultSet rs = null;
        try{
            paginationPS = connection.prepareStatement(paginationQuery);
            rs = paginationPS.executeQuery();
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
            throw new DAOException("Exception occurred in pagination function, finally block", e);
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
