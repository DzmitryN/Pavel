package connector;

import exceptions.DAOException;
import org.apache.commons.dbcp2.BasicDataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class DataSource {

    private static volatile DataSource dataSource;
    private BasicDataSource ds;

    private DataSource () throws DAOException {

        Properties properties = new Properties();
        InputStream fis = null;

        try {
            fis = this.getClass().getResourceAsStream("/db.properties");
            properties.load(fis);

            Class.forName (properties.getProperty("Driver"));

            ds = new BasicDataSource();
            ds.setUsername(properties.getProperty("User"));
            ds.setPassword(properties.getProperty("Password"));
            ds.setUrl(properties.getProperty("URL"));
            ds.setMinIdle(5);
            ds.setMaxIdle(20);
            ds.setMaxTotal(40);
            ds.setMaxOpenPreparedStatements(240);
        }catch (Exception e)
        {
            throw new DAOException("Exception occurred in DataSource: " + e);
        }
        finally {
            try {
                fis.close();
            } catch (Exception e) {
                throw new DAOException("Exception occurred in close method DataSource: " + e);
            }
        }
    }

    public static DataSource getInstance() throws DAOException{
        try{
            if(dataSource == null) {
                synchronized (DataSource.class) {
                    if(dataSource == null) {
                        dataSource = new DataSource();
                    }
                }
                 dataSource = new DataSource();
            }
        }catch (Exception ex){
            throw new DAOException("Exception occurred in getInstance method DataSource" + ex);
        }
        return dataSource;
    }

    public Connection getConnection() throws DAOException{
        try{
            return this.ds.getConnection();
        }catch (Exception ex){
            throw new DAOException("Exception occurred in getConnection method DataSource" + ex);
        }
    }
}
