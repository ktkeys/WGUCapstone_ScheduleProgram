/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.*;


/**
 * Class to set and return prepared statements. 
 * @author katil
 */
public class DBQuery {
    
     private static PreparedStatement statement; //statement reference 
   
    /**Creates setter which receives connection references.
     * @param conn connection is necessary to prepare the statement
     * @param sqlStatement
     * @throws java.sql.SQLException
     */
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException{        
       statement = conn.prepareStatement(sqlStatement);
    }
    
    /** Getter to return the prepared statement
     * @return returns the prepared statement for use in the query */  
    public static PreparedStatement getPreparedStatement(){    
        return statement; 
    }
    
  
   
    
}
