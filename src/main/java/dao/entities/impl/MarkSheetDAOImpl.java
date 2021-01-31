package dao.entities.impl;

import connector.DataSource;
import exceptions.DAOException;
import connector.AbstractDAO;
import dao.interfaces.Marks_SheetDAO;
import entities.MarksSheet;
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
    private Map<String, PreparedStatement> setOfPs = new HashMap<>();
    Connection connection;
    private static final Logger logger = LogManager.getLogger(MarkDAOImpl.class);
    static{
        BasicConfigurator.configure();
    }


    public MarkSheetDAOImpl() throws DAOException {
        try {
            connection = DataSource.getInstance().getConnection();
            logger.info("Connection created");
        }catch(Exception e){
            logger.error("Exception occurred in MarkSheetDAOImpl constructor: ", e);
            throw  new DAOException("Exception occurred in MarkSheetDAOImpl constructor: ", e);
        }
    }

    @Override
    public List<MarksSheet> findAll(int range) throws DAOException {
        List<MarksSheet> marksSheets = new ArrayList<>();
        ResultSet rs = null;
        try{
            selectAllPS=connection.prepareStatement(selectAllQuery);
            setOfPs.put("selectAllPS", selectAllPS);
            selectAllPS.setInt(1, range * 10);
            logger.info("selectAllPS created.");
            rs = selectAllPS.executeQuery();
            while (rs.next()) {
                MarksSheet marksSheetObject = getMarksSheetObject(rs);
                marksSheets.add(marksSheetObject);
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
        return marksSheets;
    }

    @Override
    public void save(MarksSheet mark) throws DAOException{
        try
        {
            savePS = connection.prepareStatement(saveQuery);
            setOfPs.put("savePS", savePS);
            savePS.setInt(1, mark.getStudent_id());
            savePS.setInt(2, mark.getSubject_id());
            savePS.setInt(3, mark.getMark_id());
            logger.info("savePS created.");
            savePS.executeUpdate();
        }catch(Exception e){
            logger.error("Exception occurred in save function" , e);
            throw new DAOException("Exception occurred in save function" , e);
        }
    }

    @Override
    public List<MarksSheet> getById(int id, int start) throws DAOException {
        List<MarksSheet> marks_sheets = new ArrayList<>();
        ResultSet rs = null;
        try{
            getByIdPS = connection.prepareStatement(getByIdQuery);
            setOfPs.put("getByIdPS", getByIdPS);
            getByIdPS.setInt(1, id);
            getByIdPS.setInt(2, start * 10);
            logger.info("getByIdPS created.");
            rs = getByIdPS.executeQuery();
            while (rs.next()) {
                MarksSheet marks_sheet = getMarksSheetObject(rs);
                marks_sheets.add(marks_sheet);
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
        return marks_sheets;
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
            logger.error("Exception occurred in pagination call MarkSheetDAO", e);
            throw new DAOException("Exception occurred in pagination call MarkSheetDAO", e);
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

    @Override
    public int paginationByStudentId(int id) throws DAOException{
        int result = 0;
        ResultSet rs = null;
        try{
        getNumberOfPagesPS = connection.prepareStatement(getNumberOfPagesQuery);
        setOfPs.put("getNumberOfPagesPS", getNumberOfPagesPS);
        getNumberOfPagesPS.setInt(1, id);
        logger.info("getNumberOfPagesPS created.");
        rs = getNumberOfPagesPS.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        }catch(Exception e){
            logger.error("Exception occurred in pagination by id call MarkSheetDAO", e);
            throw new DAOException("Exception occurred in pagination by id call MarkSheetDAO", e);
        }
        finally {
            try {
                rs.close();
            } catch (Exception e) {
                logger.error("Exception occurred in getById function, finally block", e);
                throw new DAOException("Exception occurred in getById function, finally block", e);
            }
        }
        return result;
    }

    @Override
    public void update(MarksSheet mark){}

    @Override
    public void delete(int id){}

    public void close() throws DAOException {
        DAOException daoException;
        ClosePS closeAllPreparedStatements = new ClosePS();
        daoException = closeAllPreparedStatements.closePS(setOfPs, connection);
        if(daoException != null){
            throw new DAOException(daoException);
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

