/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.sql.*;


/**
 * Creates the Customer objects. 
 * @author katil
 */
public class Customer {
    
    int customerID; 
    String fullName; 
    String address; 
    String zipCode; 
    String phoneNumber; 
    Date createDate; 
    String createdBy;
    Date lastUpdate ;
    String lastUpdatedBy ;
    int division;
    String createDateSt; 
    String lastUpdateSt; 
    String divisionName; 
    String countryName; 

   /** * Customer constructor. This constructor uses the same column that are in the database table, plus it includes the division name and country name.
     * @param customerID - unique ID assigned to the customer by the database
     * @param fullName - customer first and last name 
     * @param address  - customer address 
     * @param zipCode - customer zipcode
     * @param phoneNumber - customer phone number
     * @param createDate - date customer record created
     * @param createdBy - who created the customer record
     * @param lastUpdate - when was the last update to the customer record
     * @param lastUpdatedBy - who last updated the customer
     * @param division - division ID where the customer is located
     * @param divisionName - division name 
     * @param countryName */ 
    public Customer(int customerID, String fullName, String address, String zipCode, String phoneNumber, Date createDate, String createdBy, Date lastUpdate, String lastUpdatedBy, int division, String divisionName, String countryName) {
       this.customerID = customerID; 
       this.fullName = fullName; 
       this.address=address; 
       this.zipCode = zipCode; 
       this.phoneNumber = phoneNumber; 
       this.createDate = createDate; 
       this.createdBy = createdBy; 
       this.lastUpdate = lastUpdate; 
       this.lastUpdatedBy = lastUpdatedBy; 
       this.division = division; 
       this.divisionName = divisionName;
       this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    
    /** * Customer constructor. This constructor uses the same column that are in the database table except the customer ID. This allows for creating new customers. 
     * @param fullName - customer first and last name 
     * @param address  - customer address 
     * @param zipCode - customer zipcode
     * @param phoneNumber - customer phone number
     * @param createDate - date customer record created
     * @param createdBy - who created the customer record
     * @param lastUpdate - when was the last update to the customer record
     * @param lastUpdatedBy - who last updated the customer
     * @param division - division ID where the customer is located */
    public Customer(String fullName, String address, String zipCode, String phoneNumber, String createDate, String createdBy, String lastUpdate, String lastUpdatedBy, int division) {
       
       this.fullName = fullName; 
       this.address=address; 
       this.zipCode = zipCode; 
       this.phoneNumber = phoneNumber; 
       this.createDateSt = createDate; 
       this.createdBy = createdBy; 
       this.lastUpdateSt = lastUpdate; 
       this.lastUpdatedBy = lastUpdatedBy; 
       this.division = division; 
    }

    public String getCreateDateSt() {
        return createDateSt;
    }

    public void setCreateDateSt(String createDateSt) {
        this.createDateSt = createDateSt;
    }

    public String getLastUpdateSt() {
        return lastUpdateSt;
    }

    public void setLastUpdateSt(String lastUpdateSt) {
        this.lastUpdateSt = lastUpdateSt;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public Customer() {
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getDivision() {
        return division;
    }

    public void setDivision(int division) {
        this.division = division;
    }

    }
    

