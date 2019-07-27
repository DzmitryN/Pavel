package dao.entities.impl;
import exceptions.DAOException;
import connector.AbstractDAO;
import dao.interfaces.StudentDAO;
import entities.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl extends AbstractDAO implements StudentDAO {

    private static final String selectAllQuery = "SELECT ID, FIRST_NAME, SECOND_NAME FROM Studschema.STUDENT";
    private PreparedStatement selectAll;
    private static final String insertQuery = "INSERT INTO Studschema.STUDENT(FIRST_NAME, SECOND_NAME) VALUES (?, ?)";
    private PreparedStatement insert;
    private static final String getByIdQuery = "SELECT ID, FIRST_NAME, SECOND_NAME FROM Studschema.STUDENT WHERE ID = ?";
    private  PreparedStatement getbyIdPS;
    private static final String updateQuery = "UPDATE Studschema.STUDENT SET FIRST_NAME = ?, SECOND_NAME = ? WHERE ID = ?";
    private PreparedStatement updatePS;
    private static final String deleteQuery = "DELETE FROM Studschema.STUDENT WHERE ID = ?";
    private PreparedStatement deletePS;

    public StudentDAOImpl() throws DAOException {
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
    public List<Student> findAll() throws DAOException {
        List<Student> listStudents = new ArrayList<>();
        try(ResultSet rs = selectAll.executeQuery()) {
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("ID"));
                student.setFirstName(rs.getString("FIRST_NAME"));
                student.setSecondName(rs.getString("SECOND_NAME"));
                listStudents.add(student);
            }
        }catch(Exception e){
            throw new DAOException("Exception occurred in findAll function", e);
        }
        return listStudents;
    }

    @Override
    public void save(Student student) throws DAOException {
        try
        {
            insert.setString(1, student.getFirstName());
            insert.setString(2, student.getSecondName());
            insert.executeUpdate();
        }catch(Exception e){
            throw new DAOException("Exception occurred in save function" , e);
        }
    }

    @Override
    public Student getById(int id) throws DAOException {
        Student student = new Student();
        ResultSet rs = null;
        try{
            getbyIdPS.setInt(1, id);
            rs = getbyIdPS.executeQuery();
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
            updatePS.setString(1, student.getFirstName());
            updatePS.setString(2, student.getSecondName());
            updatePS.setInt(3, student.getId());
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
