package connector;

import exceptions.DAOException;
import org.h2.jdbcx.JdbcConnectionPool;
import java.io.InputStream;
import java.util.Properties;

public class ConnectionPool {

    public static JdbcConnectionPool jdbcConnectionPool;

    static {
        try {
            jdbcConnectionPool = getConnectionPool();
            jdbcConnectionPool.setLoginTimeout(1);
            jdbcConnectionPool.setMaxConnections(10);
            System.out.println("Instantiated number of free connections: " + ConnectionPool.jdbcConnectionPool.getActiveConnections());
        }catch(Exception e){
            System.out.println("Exception occurred in connection pool initialization block");
        }
    }

    private static JdbcConnectionPool getConnectionPool() throws DAOException{

        Properties properties = new Properties();
        InputStream fis = null;

        try {
            fis = ConnectionPool.class.getClassLoader().getResourceAsStream("/db.properties");
            properties.load(fis);

            Class.forName (properties.getProperty("Driver"));

            jdbcConnectionPool = JdbcConnectionPool.create(properties.getProperty("URL"),
                    properties.getProperty("User"), properties.getProperty("Password"));

        }catch (Exception e)
        {
            throw new DAOException("Exception occurred in AbstractDAO: " + e);
        }
        finally {
            try {
                fis.close();
            } catch (Exception e) {
                throw new DAOException("Exception occurred in fis close method ConnectionPool" + e);
            }
        }
        return jdbcConnectionPool;
    }
}
