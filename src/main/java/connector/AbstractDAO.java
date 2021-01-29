package connector;

import exceptions.DAOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;



public class AbstractDAO {

  protected  Connection connection;

     private AbstractDAO() throws DAOException {

        Properties properties = new Properties();
        InputStream fis = null;

        try {
            fis = this.getClass().getResourceAsStream("/db.properties");
            properties.load(fis);

            Class.forName (properties.getProperty("Driver"));


            //connection = DriverManager.getConnection(properties.getProperty("URL"),
                    //properties.getProperty("User"), properties.getProperty("Password"));
            //jdbcConnectionPool = JdbcConnectionPool.create(properties.getProperty("URL"),
                   // properties.getProperty("User"), properties.getProperty("Password"));
            //connection = jdbcConnectionPool.getConnection();
            //connection.setAutoCommit(false);

            /*if (connection != null) {
                System.out.println("Connected!");

            }*/

        }catch (Exception e)
        {
            throw new DAOException("Exception occurred in AbstractDAO: " + e);
        }
        finally {
            try {
                fis.close();
            } catch (Exception e) {
                throw new DAOException("Exception occurred in close method AbstractDAO" + e);
            }
        }
     }




     protected void closeConnection() throws SQLException{
        connection.close();

        if(connection.isClosed()){
            System.out.println("Connection closed");
        }
     }

}
