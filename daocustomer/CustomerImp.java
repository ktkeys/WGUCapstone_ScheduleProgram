/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daocustomer;

import country.CountryImp;
import daouser.UserImp;
import java.sql.Connection;
import java.sql.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.User;
import utils.DBConnection;
import utils.DBQuery;

/**
 * Handles all database queries to the customer table. 
 * All CRUDs are handled through this class for the customer table. 
 * @author katil
 */
public class CustomerImp {

   /**Returns all Customers. 
    Creates and returns an ObservableList containing all customers in the database
    @return returns an observable list of customer objects*/
    public static ObservableList<Customer> getAllCustomers() {
        
        ObservableList<Customer>allCustomers = FXCollections.observableArrayList();
        
        try {
            Connection conn = DBConnection.getConnection();
            String getStatement = "SELECT * FROM customers";
            DBQuery.setPreparedStatement(conn, getStatement); // creates PreparedStatementObject            
            PreparedStatement ps = DBQuery.getPreparedStatement();            
            ResultSet rs = ps.executeQuery(); 
            
            while(rs.next()){
                String divisionName = CountryImp.getFirstLevelDivName(rs.getInt("Division_ID"));
                String countryName = CountryImp.getCountryName(rs.getInt("Division_ID")); 
                allCustomers.add(new Customer(rs.getInt("Customer_ID"), rs.getString("Customer_Name"), rs.getString("Address"), rs.getString("Postal_Code"), rs.getString("Phone"), rs.getDate("Create_Date"), rs.getString("Created_By"), rs.getDate("Last_Update"), rs.getString("Last_Updated_By"), rs.getInt("Division_ID"), divisionName, countryName ));               
            }
            return allCustomers;     
        } catch (SQLException ex) {
            Logger.getLogger(UserImp.class.getName()).log(Level.SEVERE, null, ex);
        }       
      return null;        
    }
    
    /**Returns a Customer ID given a customer name. 
     Returns a Customer ID given a customer name. 
     * @param name customer name
@return returns the CustomerID in the form of an integer
     */
    public static Integer getCustomerID (String name) { 
        
        try { 
            Connection conn = DBConnection.getConnection(); 
            String getIDStmt = "SELECT * FROM customer where name = " + name; 
            DBQuery.setPreparedStatement(conn, getIDStmt);
            PreparedStatement ps = DBQuery.getPreparedStatement(); 
            ResultSet rs = ps.executeQuery(); 
            
            while (rs.next()){
                int customerID = rs.getInt("Customer_ID");
                return customerID;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerImp.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return null; 
    }

    /** * Inserts a customer into the database.Inserts a new customer into the database.
     * @param name
     * @param address
     * @param zipCode
     * @param phone
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param divisionID
     @return the newly created Customer ID */
    public static Integer insertCustomer(String name, String address, String zipCode, String phone, String createDate, String createdBy, String lastUpdate, String lastUpdatedBy, int divisionID){
        /*String name = customer.getFullName(); 
        String address = customer.getAddress(); 
        String zipCode = customer.getZipCode(); 
        String phone = customer.getPhoneNumber(); 
        String createDate = customer.getCreateDateSt(); 
        String createdBy = customer.getCreatedBy(); 
        String lastUpdate = customer.getLastUpdateSt(); 
        String lastUpdatedBy = customer.getLastUpdatedBy();
        int divisionID = customer.getDivision();*/
        int custID; 
        try {
        Connection conn = DBConnection.getConnection(); 
        String insertCust = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        DBQuery.setPreparedStatement(conn, insertCust);
        PreparedStatement ps = DBQuery.getPreparedStatement(); 
       
        ps.setString(1,name); 
        ps.setString(2, address); 
        ps.setString(3, zipCode); 
        ps.setString(4, phone); 
        ps.setString(5, createDate); 
        ps.setString(6, createdBy); 
        ps.setString(7, lastUpdate); 
        ps.setString(8, lastUpdatedBy); 
        ps.setInt(9, divisionID); 

        ps.execute(); 
        ps = conn.prepareStatement("SELECT LAST_INSERT_ID() FROM customers"); 
        ResultSet rs = ps.executeQuery(); 
        rs.next(); 
        custID = rs.getInt(1); 
        return custID; 
        } catch (SQLException ex) {
        ex.printStackTrace();
        }
      return null;  
    }
    
    /**Given customer ID will return the customer name. 
     @param ID customer ID 
     @return the customer name*/
    public static String getCustName (int ID){
        
        String custName; 
        String custNameID; 
        try {
            Connection conn = DBConnection.getConnection();
            String getStatement = "SELECT * FROM customers WHERE Customer_ID = ? ";
            DBQuery.setPreparedStatement(conn, getStatement); // creates PreparedStatementObject           
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1, ID);            
            ResultSet rs = ps.executeQuery();             
            rs.next();
            custName = rs.getString("Customer_Name");            
            custNameID =  custName; 
            return custNameID; 
            } catch (SQLException ex) {
            Logger.getLogger(UserImp.class.getName()).log(Level.SEVERE, null, ex);
        }
return null; 
    }
    
    /**Returns customer phone number given customer ID. 
     @param ID customer ID 
     @return returns a string holding the customer phone number for a given ID */  
    public static String getCustPhone (int ID){
        
        String custPhone; 
        String custNameID; 
    try {
            Connection conn = DBConnection.getConnection();
            String getStatement = "SELECT Phone FROM customers WHERE Customer_ID = ? ";
            DBQuery.setPreparedStatement(conn, getStatement); // creates PreparedStatementObject          
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1, ID);        
            ResultSet rs = ps.executeQuery();             
            rs.next();
            custPhone = rs.getString("Phone");            
            custNameID = custPhone; 
            return custPhone; 
            } catch (SQLException ex) {
            Logger.getLogger(UserImp.class.getName()).log(Level.SEVERE, null, ex);
        }
return null; 
    }
 
    /** * Updates the customer.Allows the user to make changes to various customer fields and performs an update statement to the database
     * @param custID Customer ID
     * @param name Customer Name
     * @param phone Customer Phone Number
     * @param address Customer Address
     * @param zipCode Customer Zip Code
     * @param division Customer first level division 
     * @return returns an integer showing how many rows are updated. only 1 row should be updated.  */
    public static Integer updateCustomer(int custID, String name, String phone, String address, String zipCode, int division){
    
        try{
            Connection conn = DBConnection.getConnection();
            String updateStatement =  "UPDATE customers SET Customer_Name = ?, Phone = ?, Address = ?, Postal_Code = ?, Division_ID = ? WHERE Customer_ID = ? ";
            DBQuery.setPreparedStatement(conn, updateStatement); // creates PreparedStatementObject
            
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1, name);
            ps.setString(2, phone); 
            ps.setString(3, address); 
            ps.setString(4, zipCode); 
            ps.setInt(5, division);
            ps.setInt(6, custID);            
            int row = ps.executeUpdate(); 
            return row;          
        }catch (SQLException ex) {
                        Logger.getLogger(UserImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;       
    }
    
    /** * Deletes customer from database.Deletes customer and all corresponding appointments from database. 
     Appointments will be deleted first due to foreign key restraints
     * @param custID customer ID 
     @return returns an integer on the number of rows deleted, should only return 1 */
    public static Integer deleteCustomer(int custID) {
        try {
            Connection conn = DBConnection.getConnection();        
            String deleteApptStmt = "DELETE FROM appointments WHERE customer_ID = ? ";
            DBQuery.setPreparedStatement(conn, deleteApptStmt);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1, custID);
            int apptRow = ps.executeUpdate();                        
            
            String deleteStatement = "DELETE FROM customers WHERE Customer_ID = ? ";
            DBQuery.setPreparedStatement(conn, deleteStatement); // creates PreparedStatementObject
            ps = DBQuery.getPreparedStatement();
            ps.setInt(1, custID);
            int custRow = ps.executeUpdate(); 

                   
           return custRow;  
        } catch (SQLException ex) {
            Logger.getLogger(UserImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0; 
    }

    
    
}
