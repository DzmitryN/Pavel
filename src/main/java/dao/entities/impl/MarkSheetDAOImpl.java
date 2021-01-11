package dao.entities.impl;


import exceptions.DAOException;
import connector.AbstractDAO;
import dao.interfaces.Marks_SheetDAO;
import entities.MarksSheet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarkSheetDAOImpl extends AbstractDAO implements Marks_SheetDAO {

    private static final String selectAllQuery = "Select S.id, S.first_name, S.second_name, SB.subject, m.mark\n" +
                            "from Studschema.Marks_Sheet MS\n" +
                            "join Studschema.student S\n" +
                            "on ms.student_id = s.id\n" +
                            "join Studschema.subject SB\n" +
                            "on ms.subject_id = SB.id\n" +
                            "join Studschema.mark m\n" +
                            "on ms.mark_id = m.id LIMIT %d0, 10";
    private PreparedStatement selectAll_PS;

    private static final String getByIdQuery = "Select S.id, S.first_name, S.second_name, SB.subject, m.mark\n" +
                    "from Studschema.Marks_Sheet MS\n" +
                    "join Studschema.student S\n" +
                    "on ms.student_id = s.id\n" +
                    "join Studschema.subject SB\n" +
                    "on ms.subject_id = SB.id\n" +
                    "join Studschema.mark m\n" +
                    "on ms.mark_id = m.id\n" +
                    "where s.ID = ? LIMIT %d0, 10";
    private  PreparedStatement getById_PS;

    private static final String getById_Range_Query = "Select count(1)\n" +
            "from Studschema.Marks_Sheet MS\n" +
            "join Studschema.student S\n" +
            "on ms.student_id = s.id\n" +
            "join Studschema.subject SB\n" +
            "on ms.subject_id = SB.id\n" +
            "join Studschema.mark m\n" +
            "on ms.mark_id = m.id\n" +
            "where s.ID = ?";
    private  PreparedStatement getById_Pagination_PS;

    private static final String saveQuery = "INSERT INTO Studschema.MARKS_SHEET (Student_id, subject_id, mark_id) VALUES(?, ?, ?)";
    private  PreparedStatement save_PS;

    private static final String paginationQuery = "Select count(1)\n" +
            "from Studschema.Marks_Sheet MS\n" +
            "join Studschema.student S\n" +
            "on ms.student_id = s.id\n" +
            "join Studschema.subject SB\n" +
            "on ms.subject_id = SB.id\n" +
            "join Studschema.mark m\n" +
            "on ms.mark_id = m.id";
    private PreparedStatement pagination_PS;


    public MarkSheetDAOImpl() throws DAOException {
        try {
            save_PS = connection.prepareStatement(saveQuery);
        } catch (SQLException e) {
            throw new DAOException("Exception occurred in MarkDaoConstructor." , e);
        }
    }

    public MarkSheetDAOImpl(int range) throws DAOException {
        try {
            selectAll_PS = connection.prepareStatement(String.format(selectAllQuery, range));
            getById_PS = connection.prepareStatement(String.format(getByIdQuery, range));
            save_PS = connection.prepareStatement(saveQuery);
            pagination_PS = connection.prepareStatement(paginationQuery);
            getById_Pagination_PS = connection.prepareStatement(getById_Range_Query);
        } catch (SQLException e) {
            throw new DAOException("Exception occurred in MarkDaoConstructor." , e);
        }
    }

    @Override
    public List<MarksSheet> findAll() throws DAOException {
        List<MarksSheet> marks_sheets = new ArrayList<>();
        try(ResultSet rs = selectAll_PS.executeQuery()) {
            while (rs.next()) {
                MarksSheet mark_sheet = new MarksSheet();
                mark_sheet.setID(rs.getInt("ID"));
                mark_sheet.setFirstName(rs.getString("first_name"));
                mark_sheet.setSecondName(rs.getString("second_name"));
                mark_sheet.setSubject(rs.getString("subject"));
                mark_sheet.setMark(rs.getInt("mark"));
                marks_sheets.add(mark_sheet);
            }
        }catch(Exception e){
            throw new DAOException("Exception occurred in findAll function", e);
        }
        return marks_sheets;
    }

    @Override
    public void save(MarksSheet mark) throws DAOException{
        try
        {
            save_PS.setInt(1, mark.getStudent_id());
            save_PS.setInt(2, mark.getSubject_id());
            save_PS.setInt(3, mark.getMark_id());
            save_PS.executeUpdate();
        }catch(Exception e){
            throw new DAOException("Exception occurred in save function" , e);
        }

    }

    @Override
    public List<MarksSheet> getById(int id) throws DAOException {
        List<MarksSheet> marks_sheets = new ArrayList<>();
        ResultSet rs = null;
        try{
            getById_PS.setInt(1, id);
            rs = getById_PS.executeQuery();
            while (rs.next()) {
                MarksSheet marks_sheet = new MarksSheet();
                marks_sheet.setID(rs.getInt("ID"));
                marks_sheet.setFirstName(rs.getString("first_name"));
                marks_sheet.setSecondName(rs.getString("second_name"));
                marks_sheet.setSubject(rs.getString("subject"));
                marks_sheet.setMark(rs.getInt("mark"));
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
        }
        return marks_sheets;
    }

    @Override
    public int pagination() throws DAOException{
        int result = 0;
        try(ResultSet rs = pagination_PS.executeQuery()){
            if (rs.next()) {
                result = rs.getInt(1);
            }
        }catch(Exception e){
            throw new DAOException("Exception occurred in pagination call MarkSheetDAO" + e.getMessage());
        }
        return result;
    }

    @Override
    public int pagination_ById(int id) throws DAOException{
        int result = 0;
        ResultSet rs = null;
        try{
        getById_Pagination_PS.setInt(1, id);
        rs = getById_Pagination_PS.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        }catch(Exception e){
            throw new DAOException("Exception occurred in pagination by id call MarkSheetDAO" + e.getMessage());
        }
        finally {
            try {
                rs.close();
            } catch (Exception e) {
                throw new DAOException("Exception occurred in getById function, finally block", e);
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
        DAOException daoException = null;
        try {
            getById_PS.close();
        }catch (Exception e) {
            daoException = new DAOException("Exception occurred in Close function, getById PS", e);
        }
        try {
            closeConnection();
        }catch(Exception e) {
            daoException = new DAOException("Exception occurred in Close function, closeConnection block",e);
        }
        if (daoException != null) {
            throw new DAOException("Exception occurred in close function. ", daoException);
        }
    }
}

