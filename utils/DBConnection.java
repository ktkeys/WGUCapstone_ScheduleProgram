/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
import com.mysql.jdbc.Driver;
import java.sql.DriverManager;
import java.sql.SQLException; 
import java.sql.Connection; 


import java.io.IOException;

/**
 *
 * @author katil
 */
public class DBConnection {
    
    //jdbc url parts 
    //use final so that it cannot be change 
    private static final String protocol = "jdbc"; 
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com/WJ05tVs"; 
    
    // concatenane all together 
    private static final String jdbcURL = protocol + vendorName + ipAddress; 
    
    //driver and connection interface reference 
    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver"; 
    static Connection conn; 
    
    // user name and password variables   
    private static final String username = "U05tVs"; 
    private static final String password = "53688605999"; 
   
    /**sets up database connection, returns connection object.
     * @return returns the database object */  
    public static Connection startConnection (){

    
    try {
        Class.forName(MYSQLJDBCDriver);
        //establish db connection using Driver Manager 
        conn = (Connection) DriverManager.getConnection(jdbcURL, username, password);
        }
        catch(ClassNotFoundException e){ 
            e.printStackTrace();
        }
        catch(SQLException e){              
            e.printStackTrace(); //will give more detailed exception error 
        }       
        return conn;
   }
   
   /**Closes the connection.
     * @throws java.io.IOException 
     * @throws java.sql.SQLException */ 
   public static void closeConnection() throws IOException, SQLException {
       conn.close(); 
    }
   
   /** gets connection method to serve up method whenever we need it.
     * @return returns the connection */ 
   public static Connection getConnection(){
       return conn; 
   }
    
    
    
}
