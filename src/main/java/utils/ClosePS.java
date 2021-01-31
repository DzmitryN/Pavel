package utils;

import com.sun.istack.internal.Nullable;
import exceptions.DAOException;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;

public class ClosePS {

    private static final Logger logger = LogManager.getLogger(ClosePS.class);
    static{
        BasicConfigurator.configure();
    }

    @Nullable
    public DAOException closePS(Map<String, PreparedStatement> map, Connection connection){
        DAOException daoException = null;
        for(Map.Entry<String, PreparedStatement> entry : map.entrySet()) {
            String preparedStatementName = entry.getKey();
            PreparedStatement preparedStatement = entry.getValue();
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                    logger.info(preparedStatementName + " closed");
                } catch (Exception e) {
                    logger.error(String.format("Exception occurred in Close function %s block", preparedStatementName));
                    daoException = new DAOException(String.format("Exception occurred in Close function %s block", preparedStatementName), e);
                }
            }
        }
        if(connection != null){ try {
            //closeConnection();
            connection.close();
            logger.info("Connection closed");
        }catch(Exception e) {
            logger.error("Exception occurred in Close function, closeConnection block", e);
            daoException = new DAOException("Exception occurred in Close function, closeConnection block",e);
        }}
        return daoException != null ? daoException : null;
    }
}
