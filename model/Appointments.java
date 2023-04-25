/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

/**
 * Creates the appointments objects. 
 * @author katil
 */
public class Appointments {
    
    int apptID; 
    int contactID; 
    String title; 
    String description; 
    String location; 
    String type; 
    LocalDateTime startDate; 
    LocalDate ldStartDate; 
    LocalDate ldEndDate; 
    LocalDate ldCreateDate;
    LocalDate ldUpdateDate;
    LocalTime ltStartTime; 
    LocalTime ltEndTime; 
    LocalTime ltCreateTime; 
    LocalTime ltUpdateTime; 
    String startDateStr;
    String endDateStr;
    String createDateStr;
    LocalDateTime endDate; 
    LocalDateTime createDate; 
    String createdBy; 
    LocalDateTime lastUpdate; 
    String lastUpdateStr;
    String updatedBy; 
    int customerID; 
    int  userID; 
    String contactName;

    /*
    public Appointments( int apptID, String title, String description, String location, String type, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String updatedBy, int customerID, int userID, int contactID) {
        this.apptID = apptID; 
        this.contactID = contactID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        
        this.startDate = startDate;
        this.endDate = endDate;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.updatedBy = updatedBy;
        this.customerID = customerID;
        this.userID = userID;
    }*/ 
    
    /** * Constructor for appointment object.
     * This constructor uses all the same parameters that there are in the database. For easier management, date and times have been separated out into Local Time and Local Dates rather than LocalDateTime
     * @param apptID - unique identifier of the appointment, assigned by the database
     * @param title - title of the appointment
     * @param description - description of the appointment
     * @param location - location of the appointment
     * @param type - type of appointment
     * @param ldStartDate - start date
     * @param ltStartTime - start time 
     * @param ldEndDate - end date
     * @param ltEndTime - end time 
     * @param ldCreateDate - create date
     * @param ltCreateTime - create time 
     * @param createdBy - who created the appointment. defaulted to admin 
     * @param ldUpdateDate - update date
     * @param ltUpdateTime - update time 
     * @param updatedBy - who the appointment was updated by, defaulted to admin
     * @param customerID - Customer ID 
     * @param userID - User ID 
     * @param contactID - contact ID 
     * 
     */
    public Appointments( int apptID, String title, String description, String location, String type, LocalDate ldStartDate, LocalTime ltStartTime ,LocalDate ldEndDate, LocalTime ltEndTime, LocalDate ldCreateDate, LocalTime ltCreateTime, String createdBy, LocalDate ldUpdateDate, LocalTime ltUpdateTime, String updatedBy, int customerID, int userID, int contactID) {
        this.apptID = apptID; 
        this.contactID = contactID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.ldStartDate = ldStartDate;
        this.ldEndDate = ldEndDate;
        this.ldCreateDate = ldCreateDate;
        this.ldUpdateDate = ldUpdateDate;
        this.ltStartTime = ltStartTime;
        this.ltEndTime = ltEndTime;
        this.ltCreateTime = ltCreateTime;
        this.ltUpdateTime = ltUpdateTime;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.customerID = customerID;
        this.userID = userID;
    }

    /** * Constructor for appointment object.This constructor uses all the same parameters that there are in the database plus includes the contact name.
     * For easier management, date and times have been separated out into Local Time and Local Dates rather than LocalDateTime
     * @param apptID - unique identifier of the appointment, assigned by the database
     * @param title - title of the appointment
     * @param description - description of the appointment
     * @param location - location of the appointment
     * @param type - type of appointment
     * @param ldStartDate - start date
     * @param ltStartTime - start time 
     * @param ldEndDate - end date
     * @param ltEndTime - end time 
     * @param ldCreateDate - create date
     * @param ltCreateTime - create time 
     * @param createdBy - who created the appointment. defaulted to admin 
     * @param ldUpdateDate - update date
     * @param ltUpdateTime - update time 
     * @param updatedBy - who the appointment was updated by, defaulted to admin
     * @param customerID - Customer ID 
     * @param userID - User ID 
     * @param contactID - contact ID 
     * @param contactName - contact name is included for use in table views
     * 
     */
    public Appointments( int apptID, String title, String description, String location, String type, LocalDate ldStartDate, LocalTime ltStartTime ,LocalDate ldEndDate, LocalTime ltEndTime, LocalDate ldCreateDate, LocalTime ltCreateTime, String createdBy, LocalDate ldUpdateDate, LocalTime ltUpdateTime, String updatedBy, int customerID, int userID, int contactID, String contactName) {
        this.apptID = apptID; 
        this.contactID = contactID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.ldStartDate = ldStartDate;
        this.ldEndDate = ldEndDate;
        this.ldCreateDate = ldCreateDate;
        this.ldUpdateDate = ldUpdateDate;
        this.ltStartTime = ltStartTime;
        this.ltEndTime = ltEndTime;
        this.ltCreateTime = ltCreateTime;
        this.ltUpdateTime = ltUpdateTime;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactName = contactName;
    }
    
    /** * Slimmed down appointment constructor.
     * Used to return only information that will be used for table information.
     * @param apptID - unique identifier of the appointment, assigned by the database
     * @param title - title of the appointment
     * @param description - description of the appointment
     * @param type - type of appointment
     * @param ldStartDate - start date
     * @param ltStartTime - start time 
     * @param ldEndDate - end date
     * @param ltEndTime - end time 
     * @param customerID - Customer ID 
     * @param contactID - contact ID 
     * @param contactName - contact name is included for use in table views */
   public Appointments( int apptID, String title, String description,  String type, LocalDate ldStartDate, LocalDate ldEndDate, LocalTime ltStartTime, LocalTime ltEndTime,  int customerID,  int contactID, String contactName) {
        this.apptID = apptID; 
        this.contactID = contactID;
        this.title = title;
        this.description = description;
        this.type = type;   
        this.ldStartDate = ldStartDate;
        this.ldEndDate = ldEndDate;    
        this.ltStartTime = ltStartTime;
        this.ltEndTime = ltEndTime;
        this.customerID = customerID;
        this.contactName = contactName;
    }
    
     /*   public Appointments (int apptID, String title, String description, String location, String type, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String updatedBy, int customerID, String contactName){
    
        this.apptID = apptID; 
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.updatedBy = updatedBy;
        this.customerID = customerID;
        this.contactName = contactName;
        
    }*/
    
       /* public Appointments (int apptID, String title, String description, String location, String type, String startDate, String endDate, String createDate, String createdBy, String lastUpdate, String updatedBy, int customerID, String contactName){
    
        this.apptID = apptID; 
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDateStr = startDate;
        this.endDateStr = endDate;
        this.createDateStr = createDate;
        this.createdBy = createdBy;
        this.lastUpdateStr = lastUpdate;
        this.updatedBy = updatedBy;
        this.customerID = customerID;
        this.contactName = contactName;
        
    }*/

   /**Appointment constructor. */
    public Appointments() {
    }


    public LocalDate getLdStartDate() {
        return ldStartDate;
    }

    public void setLdStartDate(LocalDate ldStartDate) {
        this.ldStartDate = ldStartDate;
    }

    public LocalDate getLdEndDate() {
        return ldEndDate;
    }

    public void setLdEndDate(LocalDate ldEndDate) {
        this.ldEndDate = ldEndDate;
    }

    public LocalDate getLdCreateDate() {
        return ldCreateDate;
    }

    public void setLdCreateDate(LocalDate ldCreateDate) {
        this.ldCreateDate = ldCreateDate;
    }

    public LocalDate getLdUpdateDate() {
        return ldUpdateDate;
    }

    public void setLdUpdateDate(LocalDate ldUpdateDate) {
        this.ldUpdateDate = ldUpdateDate;
    }

    public LocalTime getLtStartTime() {
        return ltStartTime;
    }

    public void setLtStartTime(LocalTime ltStartTime) {
        this.ltStartTime = ltStartTime;
    }

    public LocalTime getLtEndTime() {
        return ltEndTime;
    }

    public void setLtEndTime(LocalTime ltEndTime) {
        this.ltEndTime = ltEndTime;
    }

    public LocalTime getLtCreateTime() {
        return ltCreateTime;
    }

    public void setLtCreateTime(LocalTime ltCreateTime) {
        this.ltCreateTime = ltCreateTime;
    }

    public LocalTime getLtUpdateTime() {
        return ltUpdateTime;
    }

    public void setLtUpdateTime(LocalTime ltUpdateTime) {
        this.ltUpdateTime = ltUpdateTime;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public String getLastUpdateStr() {
        return lastUpdateStr;
    }

    public void setLastUpdateStr(String lastUpdateStr) {
        this.lastUpdateStr = lastUpdateStr;
    }
    
    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    
    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getApptID() {
        return apptID;
    }

    public void setApptID(int apptID) {
        this.apptID = apptID;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }



 
    
}
