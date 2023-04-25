/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Creates Country objects. 
 * @author katil
 */
public class Country {
    
     int countryID; 
    String country; 
    LocalDate createDate; 
    LocalTime createTime; 
    String createdBy; 
    String updatedBy; 
    LocalDate updateDate; 
    LocalTime updateTime; 

    /** * Constructor for the country object. Uses the same fields that the database does.
     * @param countryID - country ID
     * @param country - Friendly country Name
     * @param createDate - create Date
     * @param createTime - create Time 
     * @param createdBy - created by 
     * @param updatedBy - updated by
     * @param updateDate - update date
     * @param updateTime - update time
     */
    public Country(int countryID, String country, LocalDate createDate, LocalTime createTime, String createdBy, String updatedBy, LocalDate updateDate, LocalTime updateTime) {
        this.countryID = countryID;
        this.country = country;
        this.createDate = createDate;
        this.createTime = createTime;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.updateDate = updateDate;
        this.updateTime = updateTime;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalTime createTime) {
        this.createTime = createTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public LocalTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalTime updateTime) {
        this.updateTime = updateTime;
    }
    
}
