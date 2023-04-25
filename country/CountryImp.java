/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package country;

import java.sql.Connection; 
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import utils.DBConnection;
import utils.DBQuery;
import controller.HomeController;
import daouser.UserImp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import model.Contact;
import utils.DBConnection;
import utils.DBQuery;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import model.FirstLevelDivision;
import utils.Time;

/**
 *Performs all the CRUD operations on the country table. 
 * the result set is iterated through and returned in an observable list
 * @author katil
 */
public class CountryImp {
    
    /**Gets all countries from the database.
     * @return 
     */
    public static ObservableList<Country> getCountries (){
        ObservableList<Country>allCountries = FXCollections.observableArrayList(); 
        
        try {
            Connection conn = DBConnection.getConnection();
            String getStatement = "SELECT * FROM countries"; 
            DBQuery.setPreparedStatement(conn, getStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement(); 
            
            ResultSet rs = ps.executeQuery(); 
            while(rs.next()){
                int countryID = rs.getInt("Country_ID") ; 
                String country = rs.getString("Country"); 
                LocalDateTime ldtCreate = rs.getTimestamp("Create_Date").toLocalDateTime(); 
                LocalDate createDate = ldtCreate.toLocalDate();
                LocalTime createTime = ldtCreate.toLocalTime();  
                String createdBy = rs.getString("Created_By"); 
                String updatedBy = rs.getString("Last_Updated_By"); 
                LocalDateTime ldtUpdate = rs.getTimestamp("Last_Update").toLocalDateTime(); 
                LocalDate updateDate = ldtUpdate.toLocalDate(); 
                LocalTime updateTime = ldtUpdate.toLocalTime(); 
                
                allCountries.add(new Country(countryID, country, createDate, createTime, createdBy, updatedBy, updateDate, updateTime)); 
                
            }
            return allCountries;
        }catch (SQLException ex){
          Logger.getLogger(CountryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null; 
    }
    
    /** * Returns First Level Division based on Country ID.Used to populate combo boxes, returns the the first level divisions
     * @param countryID
     * @return 
     */
    
    public static ObservableList<FirstLevelDivision> getFirstLevelDivs (int countryID) {
    ObservableList<FirstLevelDivision>allFirstLevelDivs = FXCollections.observableArrayList(); 
        
        try {
            Connection conn = DBConnection.getConnection();
            String getStatement = "SELECT * FROM first_level_divisions WHERE COUNTRY_ID = ? "; 
            DBQuery.setPreparedStatement(conn, getStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement(); 
            ps.setInt(1, countryID);
            
            ResultSet rs = ps.executeQuery(); 
            while(rs.next()){
                int divisionID = rs.getInt("Division_ID") ; 
                String division = rs.getString("Division"); 
                LocalDateTime ldtCreate = rs.getTimestamp("Create_Date").toLocalDateTime(); 
                LocalDate createDate = ldtCreate.toLocalDate();
                LocalTime createTime = ldtCreate.toLocalTime();  
                String createdBy = rs.getString("Created_By"); 
                String updatedBy = rs.getString("Last_Updated_By"); 
                LocalDateTime ldtUpdate = rs.getTimestamp("Last_Update").toLocalDateTime(); 
                LocalDate updateDate = ldtUpdate.toLocalDate(); 
                LocalTime updateTime = ldtUpdate.toLocalTime();
                int country_ID = rs.getInt("COUNTRY_ID"); 
                
                allFirstLevelDivs.add(new FirstLevelDivision(divisionID, division, createDate, createTime, createdBy, updatedBy, updateDate, updateTime, country_ID)); 
                
            }
            return allFirstLevelDivs;
        }catch (SQLException ex){
          Logger.getLogger(CountryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null; 
    }
    
    /** * Get the country ID via Country name.Used in cases where the combo box is a list of strings.  can be used to get the country ID for database CRUDs
     @param countryName name
     * @return  returns the country ID*/
    public static Integer getCountryID (String countryName){
        int countryID; 
        
        try {
            Connection conn = DBConnection.getConnection();
            String getStatement = "SELECT * FROM countries WHERE Country = ? ";
            DBQuery.setPreparedStatement(conn, getStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement(); 
            ps.setString(1, countryName);
            
            ResultSet rs = ps.executeQuery(); 
            
            rs.next();
            countryID = rs.getInt("COUNTRY_ID");
            
            return countryID; 
            
       }catch (SQLException ex){
          Logger.getLogger(CountryImp.class.getName()).log(Level.SEVERE, null, ex);

        
        }
    return null;
    }   
    
    /** * Returns the country name based on Division ID. Allows the Country name to be returned when only the division ID is known, used to populate combo boxes when modifying appointments 
     @param divisionID ID is passed when only the division ID is known.
     * @return returns the string of the country name*/
     public static String getCountryName (int divisionID){
            String countryName;
            int countryID; 
        
        try {
            Connection conn = DBConnection.getConnection();
            String getStatement = "SELECT COUNTRY_ID FROM first_level_divisions WHERE Division_ID = ? ";
            DBQuery.setPreparedStatement(conn, getStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement(); 
            ps.setInt(1, divisionID);
            
            ResultSet rs = ps.executeQuery(); 
            
            rs.next();
            countryID = rs.getInt("COUNTRY_ID");
            
            getStatement = "SELECT Country FROM countries WHERE Country_ID = ? "; 
            DBQuery.setPreparedStatement(conn, getStatement);
            ps = DBQuery.getPreparedStatement(); 
            ps.setInt(1, countryID);
            rs = ps.executeQuery(); 
            rs.next(); 
            countryName = rs.getString("Country");
            
            return countryName; 
            
       }catch (SQLException ex){
          Logger.getLogger(CountryImp.class.getName()).log(Level.SEVERE, null, ex);

        
        }
    return null;
    }   

    /** Returns the first level division ID when the name is known. Used when combo boxes are populated with names to reverse search and get the ID for database CRUDs 
     @param fldName typically through a combo box
     * @return returns the ID of the first level division*/ 
    public static Integer getFirstLevelDivID (String fldName){
        int countryID; 
        
        try {
            Connection conn = DBConnection.getConnection();
            String getStatement = "SELECT * FROM first_level_divisions WHERE Division = ? ";
            DBQuery.setPreparedStatement(conn, getStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement(); 
            ps.setString(1, fldName);
            
            ResultSet rs = ps.executeQuery(); 
            
            rs.next();
            countryID = rs.getInt("Division_ID");
            
            return countryID; 
            
       }catch (SQLException ex){
          Logger.getLogger(CountryImp.class.getName()).log(Level.SEVERE, null, ex);
        }     
        return null;   
}
    /**Return the first level division name when only the ID is known. 
     @param fldID the first level division ID 
     * @return the friendly name of the first level division
     */
  public static String getFirstLevelDivName (int fldID ){

        try {
            Connection conn = DBConnection.getConnection();
            String getStatement = "SELECT Division FROM first_level_divisions WHERE Division_ID = ? ";
            DBQuery.setPreparedStatement(conn, getStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement(); 
            ps.setInt(1, fldID);
            
            ResultSet rs = ps.executeQuery(); 
            
            rs.next();
            String fldName = rs.getString("Division");
            
            return fldName; 
            
       }catch (SQLException ex){
          Logger.getLogger(CountryImp.class.getName()).log(Level.SEVERE, null, ex); 
        }     
 return null;   
}
}
