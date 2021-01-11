package dao.entities.impl;
import exceptions.DAOException;
import connector.AbstractDAO;
import dao.interfaces.StudentDAO;
import entities.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl extends AbstractDAO implements StudentDAO {

    private static final String selectAll_Range_Query = "SELECT ID, FIRST_NAME, SECOND_NAME FROM Studschema.STUDENT LIMIT %d0, 10";
    private PreparedStatement selectAll_Range_PS;
    private static final String selectAll_Query = "SELECT ID, FIRST_NAME, SECOND_NAME FROM Studschema.STUDENT";
    private PreparedStatement selectAll_PS;
    private static final String insertQuery = "INSERT INTO Studschema.STUDENT(FIRST_NAME, SECOND_NAME) VALUES (?, ?)";
    private PreparedStatement insert_PS;
    private static final String getByIdQuery = "SELECT ID, FIRST_NAME, SECOND_NAME FROM Studschema.STUDENT WHERE ID = ?";
    private  PreparedStatement getbyId_PS;
    private static final String updateQuery = "UPDATE Studschema.STUDENT SET FIRST_NAME = ?, SECOND_NAME = ? WHERE ID = ?";
    private PreparedStatement update_PS;
    private static final String deleteQuery = "DELETE FROM Studschema.STUDENT WHERE ID = ?";
    private PreparedStatement delete_PS;
    private static final String paginationQuery = "SELECT COUNT(ID) FROM Studschema.STUDENT";
    private PreparedStatement pagination_PS;

    public StudentDAOImpl(int range) throws DAOException {
        try {
            selectAll_Range_PS = connection.prepareStatement(String.format(selectAll_Range_Query, range));
            insert_PS = connection.prepareStatement(insertQuery);
            getbyId_PS = connection.prepareStatement(getByIdQuery);
            update_PS = connection.prepareStatement(updateQuery);
            delete_PS = connection.prepareStatement(deleteQuery);
            pagination_PS = connection.prepareStatement(paginationQuery);
        } catch (SQLException e) {
            throw new DAOException("Exception occurred in MarkDaoConstructor." , e);
        }
    }

    public StudentDAOImpl() throws DAOException {
        try {
            selectAll_PS = connection.prepareStatement(selectAll_Query);
            insert_PS = connection.prepareStatement(insertQuery);
            getbyId_PS = connection.prepareStatement(getByIdQuery);
            update_PS = connection.prepareStatement(updateQuery);
            delete_PS = connection.prepareStatement(deleteQuery);
            pagination_PS = connection.prepareStatement(paginationQuery);
        } catch (SQLException e) {
            throw new DAOException("Exception occurred in MarkDaoConstructor." , e);
        }
    }

    @Override
    public List<Student> findAll(boolean swap) throws DAOException {
        List<Student> listStudents = new ArrayList<>();
        try (ResultSet rs = (swap) ? selectAll_Range_PS.executeQuery() : selectAll_PS.executeQuery()){
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
            insert_PS.setString(1, student.getFirstName());
            insert_PS.setString(2, student.getSecondName());
            insert_PS.executeUpdate();
        }catch(Exception e){
            throw new DAOException("Exception occurred in save function" , e);
        }
    }

    @Override
    public Student getById(int id) throws DAOException {
        Student student = new Student();
        ResultSet rs = null;
        try{
            getbyId_PS.setInt(1, id);
            rs = getbyId_PS.executeQuery();
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
            update_PS.setString(1, student.getFirstName());
            update_PS.setString(2, student.getSecondName());
            update_PS.setInt(3, student.getId());
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
            insert_PS.close();
        }catch(Exception e) {
            daoException = new DAOException("Exception occurred in Close function, Insert PS", e);
        }
        try {
            getbyId_PS.close();
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
