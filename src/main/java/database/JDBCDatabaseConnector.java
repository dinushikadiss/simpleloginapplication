package database;

import util.LogConfig;
import util.Constants;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * JDBC Database Connector Class(Used Singleton Design Pattern)
 */
public class JDBCDatabaseConnector {

    private static Connection connection = null;
    private static JDBCDatabaseConnector db = null;

    private JDBCDatabaseConnector() {

        setDBConnection();
    }

    /**
     * Enables singleton design pattern
     *
     * @return JDBCDatabaseConnector instance if db is null
     */
    public static JDBCDatabaseConnector getInstance() {

        if(db == null){
            db = new JDBCDatabaseConnector();
        }
        return db;
    }

    /**
     * Returns the initiated database connection
     *
     * @return Database connection
     */
    public Connection getConnection() {

        return connection;
    }

    private void setDBConnection() {

        File configFile = new File(Constants.DB_CONFIG_FILE_PATH);
        FileReader reader = null;
        try {
            reader = new FileReader(configFile);
            Properties props = new Properties();
            props.load(reader);
            Class.forName(props.getProperty("driver"));
            connection = DriverManager.getConnection(
                    props.getProperty("url"), props.getProperty("user"), props.getProperty("password"));

        } catch (ClassNotFoundException | SQLException | IOException | NullPointerException e) {
            LogConfig.makeLog(e.getMessage());

        } finally {
            try {
                assert reader != null;
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
