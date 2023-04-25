/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Creates first level division objects. 
 * @author katil
 */
public class FirstLevelDivision {
    int divisionID; 
    String division; 
    LocalDate createDate; 
    LocalTime createTime; 
    String createdBy; 
    String updatedBy; 
    LocalDate updateDate; 
    LocalTime updateTime; 
    int countryID; 

    /**First level division constructor.
     * Similar to what is in the database but the datetime fields have been broken out into local time and local date
     * @param divisionID - unique division ID 
     * @param division - friendly division name 
     * @param createDate - create date
     * @param createTime - create time 
     * @param createdBy - created by 
     * @param updatedBy - updated by
     * @param updateDate - last updated date
     * @param updateTime - last updated time 
     * @param countryID - country ID that the first level division belongs to
     */
    public FirstLevelDivision(int divisionID, String division, LocalDate createDate, LocalTime createTime, String createdBy, String updatedBy, LocalDate updateDate, LocalTime updateTime, int countryID) {
        this.divisionID = divisionID;
        this.division = division;
        this.createDate = createDate;
        this.createTime = createTime;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.updateDate = updateDate;
        this.updateTime = updateTime;
        this.countryID = countryID; 
    }

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
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

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
    
    
    
}
