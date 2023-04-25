/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoappts;

import controller.HomeController;
import daouser.UserImp;
import java.sql.Connection;
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
import model.Customer;
import utils.Time;

/**
 * This is the class that handles all queries to the appointments tables. 
 * @author katil
 */
public class ApptImp {
 
    /** *  This will return all Appointments in the database.Returns an Observable list of all appointments in the database
     * @return returns an observable list with all appointments 
     */
    public static ObservableList<Appointments> getAllAppts() {
    
        ObservableList<Appointments>allAppts = FXCollections.observableArrayList();
        
        try {
            Connection conn = DBConnection.getConnection();
            String getStatement = "SELECT * FROM appointments";
            DBQuery.setPreparedStatement(conn, getStatement); // creates PreparedStatementObject
            PreparedStatement ps = DBQuery.getPreparedStatement();           
            ResultSet rs = ps.executeQuery();
 
            while(rs.next()){   
                int contactID = rs.getInt("Contact_ID"); 
                int userID = rs.getInt("User_ID");
                int customerID = rs.getInt("Customer_ID");
                String contactName = null;
            
                //convert to users timezone and convert to string 
                LocalDateTime startUTC = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime usersDateTimeStart = Time.convertToLocalTime(startUTC); 
                LocalDate ldStartDate = usersDateTimeStart.toLocalDate(); 
                LocalTime ltStartTime = usersDateTimeStart.toLocalTime(); 
                LocalDateTime endUTC = rs.getTimestamp("End").toLocalDateTime(); 
                LocalDateTime usersDateTimeEnd = Time.convertToLocalTime(endUTC);
                LocalDate ldEndDate = usersDateTimeEnd.toLocalDate(); 
                LocalTime ltEndTime = usersDateTimeEnd.toLocalTime();
                LocalDateTime createUTC = rs.getTimestamp("Create_Date").toLocalDateTime(); 
                LocalDateTime usersDateTimeCreate = Time.convertToLocalTime(createUTC);
                LocalDate ldCreateDate = usersDateTimeCreate.toLocalDate(); 
                LocalTime ltCreateTime = usersDateTimeCreate.toLocalTime();
                LocalDateTime updateUTC = rs.getTimestamp("Last_Update").toLocalDateTime(); 
                LocalDateTime usersDateTimeUpdate = Time.convertToLocalTime(updateUTC);
                LocalDate ldUpdateDate = usersDateTimeUpdate.toLocalDate(); 
                LocalTime ltUpdateTime = usersDateTimeUpdate.toLocalTime();

                // if you need to return the contact name as well as the contactID 
                String getContactNameStmt = "SELECT * FROM contacts WHERE contact_ID=" + contactID; 
                DBQuery.setPreparedStatement(conn, getContactNameStmt);
                PreparedStatement psContactName = DBQuery.getPreparedStatement();
                ResultSet rsContactName = psContactName.executeQuery();
                
                    while (rsContactName.next()){
                        contactName = rsContactName.getString("Contact_Name"); 
                    }
                    
                // if you need to return the user name as well as the UserID, not currently being used.  
                String getUserNameStmt = "SELECT * FROM users WHERE User_ID=" + userID; 
                DBQuery.setPreparedStatement(conn, getUserNameStmt);
                PreparedStatement psUserName = DBQuery.getPreparedStatement();
                ResultSet rsUserName = psUserName.executeQuery();
            
                    while (rsUserName.next()){
                         String userName = rsUserName.getString("User_Name"); 
                    }
            
                // if you need to return the user name as well as the UserID, not currently being used.
                String getCustomerNameStmt = "SELECT * FROM customers WHERE Customer_ID=" + customerID; 
                DBQuery.setPreparedStatement(conn, getCustomerNameStmt);
                PreparedStatement psCustomerName = DBQuery.getPreparedStatement();
                ResultSet rsCustomerName = psCustomerName.executeQuery();

                    while (rsCustomerName.next()){
                        String customerName = rsCustomerName.getString("Customer_Name");
                    }
            
            allAppts.add(new Appointments(rs.getInt("Appointment_ID"), rs.getString("Title"), rs.getString("Description"), rs.getString("Location"), rs.getString("Type"), ldStartDate, ltStartTime, ldEndDate, ltEndTime,ldCreateDate, ltCreateTime, rs.getString("Created_By"), ldUpdateDate, ltUpdateTime,  rs.getString("Last_Updated_By"), customerID, userID, contactID, contactName));
            }
            return allAppts;   
        } catch (SQLException ex) {
            Logger.getLogger(UserImp.class.getName()).log(Level.SEVERE, null, ex);
        } 
      return null;
    
}
    /** This will create an appointment. 
     @param title Title of the appointment 
     * @param description description of the appointment 
     * @param location location of the appointment 
     * @param type type of appointment
     * @param startDateTime start date and time of the appointment
     * @param endDateTime end date and time of the appointment
     * @param createDate create date of the appointment
     * @param createdBy who the appointment was created by
     * @param lastUpdate when the appointment was last updated
     * @param updatedBy who the appointment was last updated by
     * @param customerID customerID for the appointment
     * @param userID userID for the appointment
     * @param contactID contactID for the appointment
     * @return returns an integer representing the appointment id of the newly created appointment
     * 
     */
    public static Integer createAppointment (String title, String description, String location, String type, String startDateTime, String endDateTime, String createDate, 
            String createdBy, String lastUpdate, String updatedBy, int customerID, int userID, int contactID){

        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ? , ?, ?, ?, ? , ?, ?, ?, ? , ? )";
        
        try {
            Connection conn = DBConnection.getConnection();
            DBQuery.setPreparedStatement(conn, sql); // creates PreparedStatementObject
            PreparedStatement ps = DBQuery.getPreparedStatement();
                       
            ps.setString(1, title); 
            ps.setString(2, description); 
            ps.setString(3, location); 
            ps.setString(4, type); 
            ps.setString(5, startDateTime); 
            ps.setString(6, endDateTime); 
            ps.setString(7, createDate); 
            ps.setString(8, createdBy); 
            ps.setString(9, lastUpdate); 
            ps.setString(10, updatedBy); 
            ps.setInt(11, customerID);
            ps.setInt(12, userID);
            ps.setInt(13, contactID);
            ps.execute(); 

             // return newly created appt ID: 
            ps = conn.prepareStatement("SELECT LAST_INSERT_ID() FROM appointments"); 
            ResultSet rs = ps.executeQuery(); 
            rs.next(); 
            int apptID = rs.getInt(1); 
            return apptID; 
               
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;   
        }       
    }
    
    /**returns an appointment based on the appointment ID. 
     @param ID used to return the associated appointment details
     * @return returns the appointment object*/
 public static Appointments getAppt(int ID) {
    
         Appointments appt = new Appointments();

        try {
            Connection conn = DBConnection.getConnection();
            String getStatement = "SELECT * FROM appointments WHERE Appointment_ID = ?";
            DBQuery.setPreparedStatement(conn, getStatement); // creates PreparedStatementObject 
            PreparedStatement ps = DBQuery.getPreparedStatement();   
            ps.setInt(1, ID); 
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                appt.setApptID(rs.getInt("Appointment_ID")); 
                appt.setTitle(rs.getString("Title"));
                appt.setDescription(rs.getString("Description"));
                appt.setLocation(rs.getString("Location")); 
                appt.setType(rs.getString("Type")); 
                appt.setStartDate(rs.getTimestamp("Start").toLocalDateTime()); 
                appt.setEndDate(rs.getTimestamp("End").toLocalDateTime()); 
                appt.setCreateDate(rs.getTimestamp("Create_Date").toLocalDateTime()); 
                appt.setCreatedBy(rs.getString("Created_By")); 
                appt.setLastUpdate(rs.getTimestamp("Last_Update").toLocalDateTime()); 
                appt.setUpdatedBy(rs.getString("Last_Updated_By")); 
                appt.setCustomerID(rs.getInt("Customer_ID")); 
                appt.setUserID(rs.getInt("User_ID")); 
                appt.setContactID(rs.getInt("Contact_ID"));                
            }
            return appt; 
       } catch (SQLException ex) {
            Logger.getLogger(UserImp.class.getName()).log(Level.SEVERE, null, ex);
        }       
      return null;   
}
    
    /** * Query to get today's appointments. Used in the home screen to display todays appointments and determine if any are within 15 minutes of the login time
     * @return returns an Observablelist that the days appointments are added to 
     */
    public static ObservableList<Appointments> getTodaysAppts() {
    
         ObservableList<Appointments>todaysAppts = FXCollections.observableArrayList();
        
        try {
            Connection conn = DBConnection.getConnection();
            String getStatement = "SELECT * FROM appointments WHERE cast(appointments.Start AS Date) = cast(now() as date)";
            DBQuery.setPreparedStatement(conn, getStatement); // creates PreparedStatementObject           
            PreparedStatement ps = DBQuery.getPreparedStatement();            
            ResultSet rs = ps.executeQuery();

            while(rs.next()){               
                int contactID = rs.getInt("Contact_ID"); 
                int userID = rs.getInt("User_ID");
                int customerID = rs.getInt("Customer_ID");
                String contactName = null;

                //convert to users timezone and convert to string 
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
                LocalDateTime startUTC = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime usersDateTimeStart = Time.convertToLocalTime(startUTC); 
                LocalDate ldStartDate = usersDateTimeStart.toLocalDate(); 
                LocalTime ltStartTime = usersDateTimeStart.toLocalTime(); 
                LocalDateTime endUTC = rs.getTimestamp("End").toLocalDateTime(); 
                LocalDateTime usersDateTimeEnd = Time.convertToLocalTime(endUTC);
                LocalDate ldEndDate = usersDateTimeEnd.toLocalDate(); 
                LocalTime ltEndTime = usersDateTimeEnd.toLocalTime();
                LocalDateTime createUTC = rs.getTimestamp("Create_Date").toLocalDateTime(); 
                LocalDateTime usersDateTimeCreate = Time.convertToLocalTime(createUTC);
                LocalDate ldCreateDate = usersDateTimeCreate.toLocalDate(); 
                LocalTime ltCreateTime = usersDateTimeCreate.toLocalTime();
                LocalDateTime updateUTC = rs.getTimestamp("Last_Update").toLocalDateTime(); 
                LocalDateTime usersDateTimeUpdate = Time.convertToLocalTime(updateUTC);
                LocalDate ldUpdateDate = usersDateTimeUpdate.toLocalDate(); 
                LocalTime ltUpdateTime = usersDateTimeUpdate.toLocalTime();
                todaysAppts.add(new Appointments(rs.getInt("Appointment_ID"), rs.getString("Title"), rs.getString("Description"), rs.getString("Location"), rs.getString("Type"), ldStartDate, ltStartTime, ldEndDate, ltEndTime,ldCreateDate, ltCreateTime, rs.getString("Created_By"), ldUpdateDate, ltUpdateTime,  rs.getString("Last_Updated_By"), customerID, userID, contactID));
            }
            return todaysAppts;    
        } catch (SQLException ex) {
            Logger.getLogger(UserImp.class.getName()).log(Level.SEVERE, null, ex);
        }
      return null;    
}
    
    /** * Returns all customer appointments for a given day.This list can be used to verify there are no overlapping appointments.
     * @param custID CustomerID to select only that customers appointments
     * @param startDate startDate to narrow down the list further
     * @param apptID
     * @return returns an Observable List for Appointments for a particular customer and date */
    public static ObservableList<Appointments> getCustAppt(int custID, String startDate, int apptID) {
    
        Appointments appt = new Appointments();
        ObservableList<Appointments>custAppts = FXCollections.observableArrayList();

         
        
        try {
            Connection conn = DBConnection.getConnection();
            String getStatement = "SELECT * FROM appointments  WHERE NOT Appointment_ID = ? AND (Customer_ID = ? AND cast(Start as date) = ?)";
            DBQuery.setPreparedStatement(conn, getStatement); // creates PreparedStatementObject
            PreparedStatement ps = DBQuery.getPreparedStatement();            
            ps.setInt(1, apptID); 
            ps.setInt(2, custID);
            ps.setString(3, startDate); 
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                appt.setApptID(rs.getInt("Appointment_ID")); 
                appt.setTitle(rs.getString("Title"));
                appt.setDescription(rs.getString("Description"));
                appt.setLocation(rs.getString("Location")); 
                appt.setType(rs.getString("Type")); 
              
                LocalDateTime startUTC = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime usersDateTimeStart = Time.convertToLocalTime(startUTC); 
                LocalDate ldStartDate = usersDateTimeStart.toLocalDate(); 
                LocalTime ltStartTime = usersDateTimeStart.toLocalTime(); 
                appt.setLdStartDate(ldStartDate);
                appt.setLtStartTime(ltStartTime); 

                LocalDateTime endUTC = rs.getTimestamp("End").toLocalDateTime(); 
                LocalDateTime usersDateTimeEnd = Time.convertToLocalTime(endUTC);
                LocalDate ldEndDate = usersDateTimeEnd.toLocalDate(); 
                LocalTime ltEndTime = usersDateTimeEnd.toLocalTime();
                appt.setLdEndDate(ldEndDate);
                appt.setLtEndTime(ltEndTime);

                LocalDateTime createUTC = rs.getTimestamp("Create_Date").toLocalDateTime(); 
                LocalDateTime usersDateTimeCreate = Time.convertToLocalTime(createUTC);
                LocalDate ldCreateDate = usersDateTimeCreate.toLocalDate(); 
                LocalTime ltCreateTime = usersDateTimeCreate.toLocalTime();
                appt.setLdCreateDate(ldCreateDate);
                appt.setLtCreateTime(ltCreateTime);

                LocalDateTime updateUTC = rs.getTimestamp("Last_Update").toLocalDateTime(); 
                LocalDateTime usersDateTimeUpdate = Time.convertToLocalTime(updateUTC);
                LocalDate ldUpdateDate = usersDateTimeUpdate.toLocalDate(); 
                LocalTime ltUpdateTime = usersDateTimeUpdate.toLocalTime();

                appt.setLdUpdateDate(ldUpdateDate);
                appt.setLtUpdateTime(ltUpdateTime);
                appt.setCreatedBy(rs.getString("Created_By")); 
                appt.setUpdatedBy(rs.getString("Last_Updated_By")); 
                appt.setCustomerID(rs.getInt("Customer_ID")); 
                appt.setUserID(rs.getInt("User_ID")); 
                appt.setContactID(rs.getInt("Contact_ID")); 

                custAppts.add(appt);       
            }
            return custAppts;      
        } catch (SQLException ex) {
            Logger.getLogger(UserImp.class.getName()).log(Level.SEVERE, null, ex);
        }     
      return null;    
}
    /**Returns a list of all customer appointments.
     * @param custID to select 1 customer's appointments
     * @return returns appointments for 1 customer
     */
    public static ObservableList<Appointments> getCustAppt(int custID) {
    
        //DELETE IF STILL RUNS Appointments appt = new Appointments();
        ObservableList<Appointments>custAppts = FXCollections.observableArrayList();

        try {
            Connection conn = DBConnection.getConnection();
            String getStatement = "SELECT * FROM appointments WHERE Customer_ID = ?";
            DBQuery.setPreparedStatement(conn, getStatement); // creates PreparedStatementObject       
            PreparedStatement ps = DBQuery.getPreparedStatement();            
            ps.setInt(1, custID); 
            ResultSet rs = ps.executeQuery(); 
            
            while(rs.next()){
                int apptID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location"); 
                String type = rs.getString("Type"); 
   
                LocalDateTime startUTC = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime usersDateTimeStart = Time.convertToLocalTime(startUTC); // confirm if we want to convert. 
                LocalDate ldStartDate = usersDateTimeStart.toLocalDate(); 
                LocalTime ltStartTime = usersDateTimeStart.toLocalTime(); 
                LocalDate ldStart = ldStartDate;
                LocalTime ltStart = ltStartTime; 

                LocalDateTime endUTC = rs.getTimestamp("End").toLocalDateTime(); 
                LocalDateTime usersDateTimeEnd = Time.convertToLocalTime(endUTC);
                LocalDate ldEndDate = usersDateTimeEnd.toLocalDate(); 
                LocalTime ltEndTime = usersDateTimeEnd.toLocalTime();
                LocalDate ldEnd = ldEndDate;
                LocalTime ltEnd = ltEndTime;

                LocalDateTime createUTC = rs.getTimestamp("Create_Date").toLocalDateTime(); 
                LocalDateTime usersDateTimeCreate = Time.convertToLocalTime(createUTC);
                LocalDate ldCreateDate = usersDateTimeCreate.toLocalDate(); 
                LocalTime ltCreateTime = usersDateTimeCreate.toLocalTime();
                LocalDate ldCreate = ldCreateDate;
                LocalTime ltCreate = ltCreateTime;

                LocalDateTime updateUTC = rs.getTimestamp("Last_Update").toLocalDateTime(); 
                LocalDateTime usersDateTimeUpdate = Time.convertToLocalTime(updateUTC);
                LocalDate ldUpdateDate = usersDateTimeUpdate.toLocalDate(); 
                LocalTime ltUpdateTime = usersDateTimeUpdate.toLocalTime();
                LocalDate ldUpdate = ldUpdateDate;
                LocalTime ltUpdate = ltUpdateTime;

                String createdBy = rs.getString("Created_By"); 
                String updatedBy = rs.getString("Last_Updated_By"); 
                int customerID = rs.getInt("Customer_ID"); 
                int userID = rs.getInt("User_ID"); 
                int contactID = rs.getInt("Contact_ID"); 

               custAppts.add(new Appointments(apptID, title, description, location, type, ldStart, ltStart, ldEnd, ltEnd, ldCreate, ltCreate, createdBy, ldUpdate, ltUpdate, updatedBy,  customerID, userID, contactID ));               
            }
            return custAppts;     
        } catch (SQLException ex) {
            Logger.getLogger(UserImp.class.getName()).log(Level.SEVERE, null, ex);
        }
return null;
    
}
    
/** * Update appointments. Necessary variables are passed in to update an appointment
     * @param apptID
     * @param type
     * @param startDate
     * @param endDate
     * @param startTime
     * @param endTime
     * @param title
     * @param description
     * @param contactID
     * @param location
     * @return returns an Integer confirming that 1 update was made. 
 */
    public static Integer updateAppointment (int apptID, String type, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, String title, String description, int contactID, String location){
        try {
            Connection conn = DBConnection.getConnection(); 
            String updateStatement =  "UPDATE appointments SET Type = ?, Start = ?, End = ?, Title = ?, Description = ?, Contact_ID = ?, Location = ? WHERE Appointment_ID = ? ";
            DBQuery.setPreparedStatement(conn, updateStatement); // creates PreparedStatementObject
            
            // create local Date time for Database insert
            LocalDateTime ldtStartDateTime = LocalDateTime.of(startDate, startTime); 
            LocalDateTime ldtEndDateTime = LocalDateTime.of(endDate, endTime);
            //converts locat date time to string for database insert 
            String startDateTime = Time.createDateToUTC(ldtStartDateTime).toString(); 
            String endDateTime = Time.createDateToUTC(ldtEndDateTime).toString(); 
   
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1, type);
            ps.setString(2, startDateTime); 
            ps.setString(3, endDateTime); 
            ps.setString(4, title); 
            ps.setString(5, description); 
            ps.setInt(6, contactID);
            ps.setString(7, location); 
            ps.setInt(8, apptID);
            
            int row = ps.executeUpdate(); 
            return row;
    }catch (SQLException ex){
         Logger.getLogger(UserImp.class.getName()).log(Level.SEVERE, null, ex);
    }  
    return 0; 
}    
    
    
/** * Deletes an appointment. Based on appointment ID, the appointment is deleted.
     * @param apptID
     * @return  returns an integer confirming how many rows were impacted*/
    public static Integer deleteAppointment (int apptID){
        try {
            Connection conn = DBConnection.getConnection(); 
            String deleteApptStmt = "DELETE FROM appointments WHERE Appointment_ID = ? ";
            DBQuery.setPreparedStatement(conn, deleteApptStmt); // creates PreparedStatementObject    
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1, apptID);
            int row = ps.executeUpdate(); 
            return row;
        }catch (SQLException ex){
         Logger.getLogger(UserImp.class.getName()).log(Level.SEVERE, null, ex);
    }  
    return 0; 
}  

/** * Returns a result set for the Appointment by Type Report.
 * returns the count of Type by Month for a report. 
     * @return  Returns a result set so a table can be dynamically created*/
    public static ResultSet getApptTypeCount(){ 
        ObservableList<String>typeByMonth = FXCollections.observableArrayList();
        try{
            Connection conn = DBConnection.getConnection();
            String statement = "SELECT Type, Count(Type), date_format(Start, \"%m\") as Start_Month from appointments GROUP BY Start_Month, Type; ";
            DBQuery.setPreparedStatement(conn, statement); 
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ResultSet rs = ps.executeQuery();
 
    return rs;
        }catch(SQLException ex){
             Logger.getLogger(UserImp.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
    }
/** * Returns a result set for the Appointment by Type and Location Report.
 * Joins 3 different tables to allow the friendly version of the first level division name to be viewed. 
     * @return  Returns a result set so a table can be dynamically created*/
    public static ResultSet getTypeLocation(){ 
        try{
        Connection conn = DBConnection.getConnection();
        String statement = "Select appointments.Type, appointments.Customer_ID, customers.Division_ID, first_level_divisions.Division from ((appointments INNER JOIN customers ON appointments.Customer_ID=customers.Customer_ID) INNER JOIN first_level_divisions ON customers.Division_ID= first_level_divisions.Division_ID) ";
        DBQuery.setPreparedStatement(conn, statement); 
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ResultSet rs = ps.executeQuery();

    return rs;
        }catch(SQLException ex){
                     Logger.getLogger(UserImp.class.getName()).log(Level.SEVERE, null, ex);
            }
    return null;
}


/** *  Returns all appointments for a specified contact.
 * Returns the all appointments including the contact name
     * @return Returns an observable List of slimmed down appointments which only included contact ID, name, appointment ID, type, title, description, and start and end dates and times*/
    public static ObservableList<Appointments> getContactAppt() {
    
        Appointments appt = new Appointments();
        ObservableList<Appointments>contactAppts = FXCollections.observableArrayList();

        try {
            Connection conn = DBConnection.getConnection();
            String getStatement = "SELECT appointments.Contact_ID, contacts.Contact_Name, appointments.Appointment_ID, appointments.Title, appointments.Type, appointments.Description, appointments.Start, appointments.End, appointments.Customer_ID FROM appointments INNER JOIN contacts ON appointments.Contact_ID=contacts.Contact_ID";
            DBQuery.setPreparedStatement(conn, getStatement); // creates PreparedStatementObject            
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                int apptID = rs.getInt("appointments.Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");                
                String type = rs.getString("Type"); 
                String contactName = rs.getString("Contact_Name"); 
                
              
                LocalDateTime startUTC = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime usersDateTimeStart = Time.convertToLocalTime(startUTC); // confirm if we want to convert. 
                LocalDate ldStartDate = usersDateTimeStart.toLocalDate(); 
                LocalTime ltStartTime = usersDateTimeStart.toLocalTime(); 
                LocalDate ldStart = ldStartDate;
                LocalTime ltStart = ltStartTime; 

                LocalDateTime endUTC = rs.getTimestamp("End").toLocalDateTime(); 
                LocalDateTime usersDateTimeEnd = Time.convertToLocalTime(endUTC);
                LocalDate ldEndDate = usersDateTimeEnd.toLocalDate(); 
                LocalTime ltEndTime = usersDateTimeEnd.toLocalTime();
                LocalDate ldEnd = ldEndDate;
                LocalTime ltEnd = ltEndTime;
     
                int customerID = rs.getInt("Customer_ID");                
                int contactID = rs.getInt("Contact_ID"); 
                            
               contactAppts.add(new Appointments(apptID, title, description, type, ldStart, ldEnd, ltStart,  ltEnd, customerID,  contactID, contactName ));
               
            }
            return contactAppts;          
        } catch (SQLException ex) {
            Logger.getLogger(UserImp.class.getName()).log(Level.SEVERE, null, ex);
        }      
      return null;
    
}
    
    /**Returns all contact appointments based on the contact ID.
     * Returns all appointments for a given contact ID 
     * @param ID  contact ID 
     * @return 
     */
    public static ObservableList<Appointments> getContactAppt(int ID) {
    
        Appointments appt = new Appointments();
        ObservableList<Appointments>contactAppts = FXCollections.observableArrayList();

         
        
        try {
            Connection conn = DBConnection.getConnection();
            String getStatement = "SELECT * FROM appointments WHERE Contact_ID = ?";
            DBQuery.setPreparedStatement(conn, getStatement); // creates PreparedStatementObject            
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1, ID);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                int contactID = rs.getInt("Contact_ID"); 
                int userID = rs.getInt("User_ID");
                int customerID = rs.getInt("Customer_ID");
                String contactName = null;

                //convert to users timezone and convert to string 
                LocalDateTime startUTC = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime usersDateTimeStart = Time.convertToLocalTime(startUTC); 
                LocalDate ldStartDate = usersDateTimeStart.toLocalDate(); 
                LocalTime ltStartTime = usersDateTimeStart.toLocalTime(); 
                LocalDateTime endUTC = rs.getTimestamp("End").toLocalDateTime(); 
                LocalDateTime usersDateTimeEnd = Time.convertToLocalTime(endUTC);
                LocalDate ldEndDate = usersDateTimeEnd.toLocalDate(); 
                LocalTime ltEndTime = usersDateTimeEnd.toLocalTime();
                //String usersDateTimeEndStr = usersDateTimeEnd.toString().replace("T", " ");
                LocalDateTime createUTC = rs.getTimestamp("Create_Date").toLocalDateTime(); 
                LocalDateTime usersDateTimeCreate = Time.convertToLocalTime(createUTC);
                LocalDate ldCreateDate = usersDateTimeCreate.toLocalDate(); 
                LocalTime ltCreateTime = usersDateTimeCreate.toLocalTime();
                LocalDateTime updateUTC = rs.getTimestamp("Last_Update").toLocalDateTime(); 
                LocalDateTime usersDateTimeUpdate = Time.convertToLocalTime(updateUTC);
                LocalDate ldUpdateDate = usersDateTimeUpdate.toLocalDate(); 
                LocalTime ltUpdateTime = usersDateTimeUpdate.toLocalTime();

                // if you need to return the contact name as well as the contactID 
                String getContactNameStmt = "SELECT * FROM contacts WHERE contact_ID=" + contactID; 
                DBQuery.setPreparedStatement(conn, getContactNameStmt);
                PreparedStatement psContactName = DBQuery.getPreparedStatement();
                ResultSet rsContactName = psContactName.executeQuery();
                while (rsContactName.next()){
                    contactName = rsContactName.getString("Contact_Name"); 
                }
                
                // if you need to return the contact name as well as the contactID 
                String getUserNameStmt = "SELECT * FROM users WHERE User_ID=" + userID; 
                DBQuery.setPreparedStatement(conn, getUserNameStmt);
                PreparedStatement psUserName = DBQuery.getPreparedStatement();
                ResultSet rsUserName = psUserName.executeQuery();
                while (rsUserName.next()){
                    String userName = rsUserName.getString("User_Name"); 
                }
                
                // if you need to return the contact name as well as the contactID 
                String getCustomerNameStmt = "SELECT * FROM customers WHERE Customer_ID=" + customerID; 
                DBQuery.setPreparedStatement(conn, getCustomerNameStmt);
                PreparedStatement psCustomerName = DBQuery.getPreparedStatement();
                ResultSet rsCustomerName = psCustomerName.executeQuery();
                while (rsCustomerName.next()){
                    String customerName = rsCustomerName.getString("Customer_Name");
                }
            
            contactAppts.add(new Appointments(rs.getInt("Appointment_ID"), rs.getString("Title"), rs.getString("Description"), rs.getString("Location"), rs.getString("Type"), ldStartDate, ltStartTime, ldEndDate, ltEndTime,ldCreateDate, ltCreateTime, rs.getString("Created_By"), ldUpdateDate, ltUpdateTime,  rs.getString("Last_Updated_By"), customerID, userID, contactID, contactName));
               
            }
         return contactAppts;          
        } catch (SQLException ex) {
            Logger.getLogger(UserImp.class.getName()).log(Level.SEVERE, null, ex);
        }
  
      return null;
    
}
}
