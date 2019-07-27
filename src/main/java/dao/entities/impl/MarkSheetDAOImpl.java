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
                            "on ms.mark_id = m.id";
    private PreparedStatement selectAll;

    private static final String getByIdQuery = "Select S.id, S.first_name, S.second_name, SB.subject, m.mark\n" +
                    "from Studschema.Marks_Sheet MS\n" +
                    "join Studschema.student S\n" +
                    "on ms.student_id = s.id\n" +
                    "join Studschema.subject SB\n" +
                    "on ms.subject_id = SB.id\n" +
                    "join Studschema.mark m\n" +
                    "on ms.mark_id = m.id\n" +
                    "where s.ID = ?";
    private  PreparedStatement getbyIdPS;

    private static final String saveQuery = "INSERT INTO Studschema.MARKS_SHEET (Student_id, subject_id, mark_id) VALUES(?, ?, ?)";
    private  PreparedStatement savePS;


    public MarkSheetDAOImpl() throws DAOException {
        try {
            selectAll = connection.prepareStatement(selectAllQuery);
            getbyIdPS = connection.prepareStatement(getByIdQuery);
            savePS = connection.prepareStatement(saveQuery);
        } catch (SQLException e) {
            throw new DAOException("Exception occurred in MarkDaoConstructor." , e);
        }
    }

    @Override
    public List<MarksSheet> findAll() throws DAOException {
        List<MarksSheet> marks_sheets = new ArrayList<>();
        try(ResultSet rs = selectAll.executeQuery()) {
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
            savePS.setInt(1, mark.getStudent_id());
            savePS.setInt(2, mark.getSubject_id());
            savePS.setInt(3, mark.getMark_id());
            savePS.executeUpdate();
        }catch(Exception e){
            throw new DAOException("Exception occurred in save function" , e);
        }

    }

    @Override
    public List<MarksSheet> getById(int id) throws DAOException {
        List<MarksSheet> marks_sheets = new ArrayList<>();
        ResultSet rs = null;
        try{
            getbyIdPS.setInt(1, id);
            rs = getbyIdPS.executeQuery();
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
    public void update(MarksSheet mark){

    }

    @Override
    public void delete(int id){

    }

    public void close() throws DAOException {
        DAOException daoException = null;
        try {
            getbyIdPS.close();
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

