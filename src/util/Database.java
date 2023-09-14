package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author yongc
 */
public class Database {
    private Connection conn;
    
    public Database(String dbHost, String dbUser, String dbPass, String dbName){
        String dbUrl = "jdbc:mysql://" + dbHost + "/" + dbName;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        } catch (ClassNotFoundException | SQLException e) {
        }
    }
    
    public Database(String sqliteFilePath) {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + sqliteFilePath);
        } catch (ClassNotFoundException | SQLException e) {
        }
    }
    
    public void closeConn(){
        try{
            if (conn != null){
                conn.close();
            }
        }catch(SQLException e){
        }
    }
    
    public Connection getConnection() {
        return conn;
    }
    
    public ResultSet runSql(String sqlText) throws SQLException {
        ResultSet resultSet = null;

        try {
            Statement statement = conn.createStatement();
            resultSet = statement.executeQuery(sqlText);
        } finally {
        }

        return resultSet;
    }
}
