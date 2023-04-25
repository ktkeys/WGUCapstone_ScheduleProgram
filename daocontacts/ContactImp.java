/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daocontacts;

import daouser.UserImp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.Customer;
import utils.DBConnection;
import utils.DBQuery;

/**
 *This class handles all database queries for the contact table. 
 * @author katil
 */
public class ContactImp {
    
    /**This will return all Contacts. 
     This is used to return all contacts to an observable list so it can be used in contact forms. 
     @return observable list of contact objects*/
    public static ObservableList<Contact> getAllContacts() {
        
        ObservableList<Contact>allContacts = FXCollections.observableArrayList();
        
        try {
            Connection conn = DBConnection.getConnection();
            String getStatement = "SELECT * FROM contacts";
            DBQuery.setPreparedStatement(conn, getStatement); // creates PreparedStatementObject            
            PreparedStatement ps = DBQuery.getPreparedStatement();           
            ResultSet rs = ps.executeQuery(); 
            
            while(rs.next()){
                allContacts.add(new Contact(rs.getInt("Contact_ID"), rs.getString("Contact_Name"), rs.getString("Email")));               
            }
            return allContacts;                                  
        } catch (SQLException ex) {
            Logger.getLogger(UserImp.class.getName()).log(Level.SEVERE, null, ex);
        }       
      return null;
}
    
    /**Returns the contactName given a contact ID.
     * @param ID Contact ID
     @return String with the contact name.  */
    public static String getContactName (int ID){
        
            String contactName; 
            String contactNameID; 
    try {
            Connection conn = DBConnection.getConnection();
            String getStatement = "SELECT * FROM contacts WHERE Contact_ID = ? ";
            DBQuery.setPreparedStatement(conn, getStatement); // creates PreparedStatementObject            
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1, ID);            
            ResultSet rs = ps.executeQuery();             
            rs.next();
            contactName = rs.getString("Contact_Name");            
            contactNameID = contactName + " " + ID ; 
            return contactNameID; 
            } catch (SQLException ex) {
            Logger.getLogger(UserImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    return null; 
    }
    
    /**Updates modified contact information. 
     This will take the new fields of a contact and update the database. 
     @param ID contactID 
     @param name contact name 
     @param email contact's email
     @return an integer is returned to verify that a row was updated. */
    public static Integer updateContact (int ID, String name, String email){
    try{
            Connection conn = DBConnection.getConnection();
            String updateStatement =  "UPDATE contacts SET Contact_Name = ?, Email = ? WHERE Contact_ID = ? ";
            DBQuery.setPreparedStatement(conn, updateStatement); // creates PreparedStatementObject           
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1, name);
            ps.setString(2, email); 
            ps.setInt(3, ID);                       
            int row = ps.executeUpdate(); 
            return row;
    }catch (SQLException ex) {
                        Logger.getLogger(UserImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;    
    }
    
    
    public static Integer addContact ( String name, String email){
    try{    
        int contactID; 
            Connection conn = DBConnection.getConnection();
            String updateStatement =  "INSERT INTO contacts (Contact_Name, Email) VALUES (?, ?)";
            DBQuery.setPreparedStatement(conn, updateStatement); // creates PreparedStatementObject           
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1, name);
            ps.setString(2, email); 
            ps.execute(); 
            ps = conn.prepareStatement("SELECT LAST_INSERT_ID() FROM contacts");
            ResultSet rs = ps.executeQuery(); 
            rs.next(); 
            contactID = rs.getInt(1); 
            return contactID; 
    }catch (SQLException ex) {
                        Logger.getLogger(UserImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;    
    }
    
    
    public static Integer deleteContact(int contactID){
    try {
        
        System.out.println("The contact ID is " + contactID); 
            Connection conn = DBConnection.getConnection();  
            String deleteApptStmt = "DELETE FROM appointments WHERE Contact_ID = ?";
            DBQuery.setPreparedStatement(conn, deleteApptStmt); 
            PreparedStatement ps = DBQuery.getPreparedStatement(); 
            ps.setInt(1, contactID);
            int apptRow = ps.executeUpdate(); 
            
            
            String deleteStatement = "DELETE FROM contacts WHERE Contact_ID = ? ";
             DBQuery.setPreparedStatement(conn, deleteStatement);
            ps = DBQuery.getPreparedStatement();
            ps.setInt(1, contactID);
            int contactRow = ps.executeUpdate(); 
  
            
           return contactRow;  
        } catch (SQLException ex) {
            Logger.getLogger(UserImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0; 
    
    }
}
