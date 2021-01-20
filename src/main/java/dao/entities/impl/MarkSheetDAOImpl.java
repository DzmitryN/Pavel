package dao.entities.impl;


import connector.ConnectionPool;
import exceptions.DAOException;
import connector.AbstractDAO;
import dao.interfaces.Marks_SheetDAO;
import entities.MarksSheet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static utils.Constants.LINES_DISPLAY_PER_PAGE;

public class MarkSheetDAOImpl /*extends AbstractDAO*/ implements Marks_SheetDAO  {

    private static final String selectAllQuery = String.format("Select S.id, S.first_name, S.second_name, SB.subject, m.mark\n" +
                            "from Studschema.Marks_Sheet MS\n" +
                            "join Studschema.student S\n" +
                            "on ms.student_id = s.id\n" +
                            "join Studschema.subject SB\n" +
                            "on ms.subject_id = SB.id\n" +
                            "join Studschema.mark m\n" +
                            "on ms.mark_id = m.id LIMIT ?, %d", LINES_DISPLAY_PER_PAGE);
    private PreparedStatement selectAllPS;

    private static final String getByIdQuery = String.format("Select S.id, S.first_name, S.second_name, SB.subject, m.mark\n" +
                    "from Studschema.Marks_Sheet MS\n" +
                    "join Studschema.student S\n" +
                    "on ms.student_id = s.id\n" +
                    "join Studschema.subject SB\n" +
                    "on ms.subject_id = SB.id\n" +
                    "join Studschema.mark m\n" +
                    "on ms.mark_id = m.id\n" +
                    "where s.ID = ? LIMIT ?, %d", LINES_DISPLAY_PER_PAGE);
    private  PreparedStatement getByIdPS;

    private static final String getNumberOfPagesQuery = "Select count(1)\n" +
            "from Studschema.Marks_Sheet MS\n" +
            "join Studschema.student S\n" +
            "on ms.student_id = s.id\n" +
            "join Studschema.subject SB\n" +
            "on ms.subject_id = SB.id\n" +
            "join Studschema.mark m\n" +
            "on ms.mark_id = m.id\n" +
            "where s.ID = ?";
    private  PreparedStatement getNumberOfPagesPS;

    private static final String saveQuery = "INSERT INTO Studschema.MARKS_SHEET (Student_id, subject_id, mark_id) VALUES(?, ?, ?)";
    private  PreparedStatement savePS;

    private static final String paginationQuery = "Select count(1)\n" +
            "from Studschema.Marks_Sheet MS\n" +
            "join Studschema.student S\n" +
            "on ms.student_id = s.id\n" +
            "join Studschema.subject SB\n" +
            "on ms.subject_id = SB.id\n" +
            "join Studschema.mark m\n" +
            "on ms.mark_id = m.id";
    private PreparedStatement paginationPS;

    public MarkSheetDAOImpl() throws DAOException {}

    @Override
    public List<MarksSheet> findAll(int range) throws DAOException {
        List<MarksSheet> marksSheets = new ArrayList<>();
        ResultSet rs = null;
        Connection connection = null;
        try{
            System.out.println("MarkSheetDAO find all, number of free connections: " + ConnectionPool.jdbcConnectionPool.getActiveConnections());
            connection = ConnectionPool.jdbcConnectionPool.getConnection();
            selectAllPS=connection.prepareStatement(selectAllQuery);
            selectAllPS.setInt(1, range * 10);
            rs = selectAllPS.executeQuery();
            while (rs.next()) {
                MarksSheet marksSheetObject = getMarksSheetObject(rs);
                marksSheets.add(marksSheetObject);
            }
        }catch(Exception e){
            throw new DAOException("Exception occurred in findAll function", e);
        }finally {
            try {
                rs.close();
            } catch (Exception e) {
                throw new DAOException("Exception occurred in findAll function, finally block", e);
            }
            try{
                selectAllPS.close();
                if(connection != null) connection.close();
            }catch (Exception ex){
                throw new DAOException("Exception occurred in selectAllPS Close function", ex);
            }
        }
        return marksSheets;
    }

    @Override
    public void save(MarksSheet mark) throws DAOException{
        Connection connection = null;
        try
        {
            System.out.println("MarkSheetDAO save, number of free connections: " + ConnectionPool.jdbcConnectionPool.getActiveConnections());
            connection = ConnectionPool.jdbcConnectionPool.getConnection();
            savePS = connection.prepareStatement(saveQuery);
            savePS.setInt(1, mark.getStudent_id());
            savePS.setInt(2, mark.getSubject_id());
            savePS.setInt(3, mark.getMark_id());
            savePS.executeUpdate();
        }catch(Exception e){
            throw new DAOException("Exception occurred in save function" , e);
        }finally{
            try{
                savePS.close();
                if(connection != null) connection.close();
            }catch (Exception ex){
                throw new DAOException("Exception occurred in savePS Close function", ex);
            }
        }

    }

    @Override
    public List<MarksSheet> getById(int id, int start) throws DAOException {
        List<MarksSheet> marks_sheets = new ArrayList<>();
        ResultSet rs = null;
        Connection connection = null;
        try{
            System.out.println("MarkSheetDAO get by id, number of free connections: " + ConnectionPool.jdbcConnectionPool.getActiveConnections());
            connection = ConnectionPool.jdbcConnectionPool.getConnection();
            getByIdPS = connection.prepareStatement(getByIdQuery);
            getByIdPS.setInt(1, id);
            getByIdPS.setInt(2, start * 10);
            rs = getByIdPS.executeQuery();
            while (rs.next()) {
                MarksSheet marks_sheet = getMarksSheetObject(rs);
                marks_sheets.add(marks_sheet);
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
            try{
                getByIdPS.close();
                if(connection != null) connection.close();
            }catch (Exception ex){
                throw new DAOException("Exception occurred in getByIdPS Close function", ex);
            }
        }
        return marks_sheets;
    }

    @Override
    public int pagination() throws DAOException{
        int result = 0;
        ResultSet rs = null;
        Connection connection = null;
        try{
            System.out.println("MarkSheetDAO pagination, number of free connections: " + ConnectionPool.jdbcConnectionPool.getActiveConnections());
            connection = ConnectionPool.jdbcConnectionPool.getConnection();
            paginationPS = connection.prepareStatement(paginationQuery);
            rs = paginationPS.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        }catch(Exception e){
            throw new DAOException("Exception occurred in pagination call MarkSheetDAO", e);
        }finally {
            try {
                rs.close();
            } catch (Exception e) {
                throw new DAOException("Exception occurred in getById function, finally block", e);
            }
            try{
                paginationPS.close();
                if(connection != null) connection.close();
            }catch (Exception ex){
                throw new DAOException("Exception occurred in paginationPS Close function", ex);
            }
        }
        return result;
    }

    @Override
    public int paginationByStudentId(int id) throws DAOException{
        int result = 0;
        ResultSet rs = null;
        Connection connection = null;
        try{
        System.out.println("MarkSheetDAO paginationByStudentId, number of free connections: " + ConnectionPool.jdbcConnectionPool.getActiveConnections());
        connection = ConnectionPool.jdbcConnectionPool.getConnection();
        getNumberOfPagesPS = connection.prepareStatement(getNumberOfPagesQuery);
        getNumberOfPagesPS.setInt(1, id);
        rs = getNumberOfPagesPS.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        }catch(Exception e){
            throw new DAOException("Exception occurred in pagination by id call MarkSheetDAO", e);
        }
        finally {
            try {
                rs.close();
            } catch (Exception e) {
                throw new DAOException("Exception occurred in getById function, finally block", e);
            }
            try{
                getNumberOfPagesPS.close();
                if(connection != null) connection.close();
            }catch (Exception ex){
                throw new DAOException("Exception occurred in getNumberOfPagesPS Close function", ex);
            }
        }
        return result;
    }

    @Override
    public void update(MarksSheet mark){

    }

    @Override
    public void delete(int id){

    }

    public void close() throws DAOException {
        try {
            //closeConnection();
            //connection.close();
        }catch(Exception e) {
            throw new DAOException("Exception occurred in MarkSheetDAO Close function, closeConnection block",e);
        }
    }

    private MarksSheet getMarksSheetObject(ResultSet rs) throws SQLException {
        MarksSheet marks_sheet = new MarksSheet();
        marks_sheet.setID(rs.getInt("ID"));
        marks_sheet.setFirstName(rs.getString("first_name"));
        marks_sheet.setSecondName(rs.getString("second_name"));
        marks_sheet.setSubject(rs.getString("subject"));
        marks_sheet.setMark(rs.getInt("mark"));
        return marks_sheet;
    }
}

