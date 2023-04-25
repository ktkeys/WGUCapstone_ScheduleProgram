/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daouser;

import java.util.List;
import model.User;
import utils.DBConnection;
import java.sql.Connection; 
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DBQuery;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *This class handles logging users in. 
 * 
 * @author katil
 */
public class UserImp {
    
    /**Returns a user if the username and password is found in the database. 
     After the username and password is confirmed, parameter are then used to return a user object.  
     @param username username entered by person logging in 
     @param password password entered by person logging in
     @return will return a user if a match is found otherwise will return null. */
    public static User getUser(String username, String password) {
        User resultUser = new User(); 
        try {
            Connection conn = DBConnection.getConnection();
            String selectStatement = "select * FROM users WHERE User_Name = ? AND Password = ?";           
            DBQuery.setPreparedStatement(conn, selectStatement); // creates PreparedStatementObject             
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1, username);
            ps.setString(2, password); 
            ResultSet rs = ps.executeQuery();           
            
            while (rs.next()){
                resultUser = new User(rs.getInt("User_ID"), rs.getString("User_Name")); 
                return resultUser;
            }   
        } catch (SQLException ex) {
            Logger.getLogger(UserImp.class.getName()).log(Level.SEVERE, null, ex);
        }       
        return null;        
    } 
    
    /**Returns a boolean if the username and password combo is found. 
     If the username and password combo isn ot found, boolean is changed to false. 
     * @param username username entered by person logging in 
     @param password password entered by person logging in
     @return will return either true or false based on whether or not the combination is found.
     */
    public static boolean getUnPw (String username, String password){
        boolean login = true;  
        User currentUser = new User(); 
            try {
            Connection conn = DBConnection.getConnection();
            String selectStatement = "select * FROM users WHERE User_Name = ? AND Password = ?";          
            DBQuery.setPreparedStatement(conn, selectStatement); // creates PreparedStatementObject            
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1, username);
            ps.setString(2, password); 
            ResultSet rs = ps.executeQuery();
 
            if (rs.next()== false){
                login = false;          
            } 
            } catch (SQLException ex) {
            }   
        return login;        
    }
    
    /**Used to return all Users. 
     Used to return all users so that a User can get selected when creating an appointment. 
     @return Returns an observable list containing the UserID and Usernames*/
    public static ObservableList<User> getAllUser() {
        ObservableList<User> allUsers = FXCollections.observableArrayList(); 
        try {
             Connection conn = DBConnection.getConnection();
            String selectStatement = "select * FROM users"; 
            DBQuery.setPreparedStatement(conn, selectStatement); // creates PreparedStatementObject             
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int userID = rs.getInt("User_ID"); 
                String username = rs.getString("User_Name"); 
                allUsers.add(new User(userID, username)); 
            }   
        } catch (SQLException ex) {
            Logger.getLogger(UserImp.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return allUsers; 
        
    } 

    

    
}
