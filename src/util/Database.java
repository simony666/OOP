package util;

import Artist.Artist;
import Performance.Performance;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.lang.model.util.Types;

/**
 *
 * @author yongc
 */
public class Database {
    private static Connection conn;
    private Config config = new Config();
    
    
    public Database(){       
        String dbUrl = "jdbc:mysql://" + config.get("dbHost") + "/" + config.get("dbName");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbUrl, config.get("dbUser"), config.get("dbPass"));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    private void closeConn(){
        try{
            if (conn != null){
                conn.close();
            }
        }catch(SQLException e){
        }
    }
    
    public static Connection getConnection() {
        return conn;
    }
    
    public static ResultSet runQuery(String sqlText){
        ResultSet resultSet = null;

        try {
            Statement statement = conn.createStatement();
            resultSet = statement.executeQuery(sqlText);
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return resultSet;
    }
    
    public static int runUpdate(String sqlText){
        int sucess = 0;

        try {
            Statement statement = conn.createStatement();
            sucess = statement.executeUpdate(sqlText);
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return sucess;
    }

}
