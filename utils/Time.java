/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import daoappts.ApptImp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;

/**
 * Handles functions that have to do with date and times. 
 * Converts time from UCT to the users local time, checks business hours, and overlapping appointments. 
 * @author katil
 */
public class Time {
    
    /** * Changes the time to UTC. Takes the users local time and converts it to UTC for database
     @param ldt LocalDateTime to be converted
     * @return returns a local date time in UTC*/ 
    public static LocalDateTime createDateToUTC (LocalDateTime ldt) {
    
    ZoneId usersZid = ZoneId.systemDefault(); 
      
    LocalDateTime createDateTime = ldt;
    ZonedDateTime zdtCreate = createDateTime.atZone(usersZid); 
    ZonedDateTime utcCreate = zdtCreate.withZoneSameInstant(ZoneId.of("UTC")); 
    createDateTime = utcCreate.toLocalDateTime(); 
    String createDateTimeUTC = createDateTime.toLocalDate() + " " + createDateTime.toLocalTime(); 
    return createDateTime; 
    }
    
    /**Converts UTC back to the users local Time.
     * @param ldt - LocalDateTime passed in UTC
     * @return returns localDateTime in the users default time */
    public static LocalDateTime convertToLocalTime (LocalDateTime ldt) {   
    ZoneId usersZid = ZoneId.systemDefault();  
    ZoneId utcZoneId = ZoneId.of("UTC"); 
    ZonedDateTime zdtUTC = ZonedDateTime.of(ldt, utcZoneId);        
    ZonedDateTime zdtLocal = ZonedDateTime.ofInstant(zdtUTC.toInstant(), usersZid); 
    LocalDateTime usersDateTime = zdtLocal.toLocalDateTime(); 
    return usersDateTime; 
    }
    
    /** * Checks to see if appointments are overlapping.Checks to see if there are overlapping appointments for customer appointments.
     * @param startA start time for the appointment 
     * @param endA end time for the appointment
     * @param custID customerID so that other appointments can be reviewed
     * @param startDate start date 
     * @param apptID 
     * @return  a boolean indicating if appointments overlap*/ 
    
    public static boolean isOverlapping (LocalTime startA, LocalTime endA, int custID, LocalDate startDate, int apptID){
        ObservableList<Appointments>custAppts = FXCollections.observableArrayList();
        custAppts = ApptImp.getCustAppt(custID, startDate.toString(), apptID); 
        

        LocalTime startB = null; 
        LocalTime endB = null; 
        boolean isOverlapping = false;
        int i = 1; 
        
        for (Appointments appt : custAppts){
            startB = appt.getLtStartTime();            
            endB = appt.getLtEndTime();             
            if (startA.isBefore(endB) && startB.isBefore(endA)) {
                isOverlapping = true;
                break;
            }      
        }      
        return isOverlapping; 
    }
    
    /**Checks if appointment is between 8:00 AM EST and 10PM IST.
     * @param ltStart Start time of appointment
     * @param ltEnd End time of appointment 
     * @param ldStart Start date of appointment 
     * @param ldEnd end date of appointment 
     * @return  returns a boolean indicating if appointment is within business hours*/
    public static boolean businessHours (LocalTime ltStart, LocalTime ltEnd, LocalDate ldStart, LocalDate ldEnd){
        
        LocalTime ltOpen = LocalTime.of(12, 00, 00);
        LocalTime ltClose = LocalTime.of(02, 00, 00); 
        LocalDate today = LocalDate.now(); 

        LocalDateTime open = LocalDateTime.of(today, ltOpen);  
        LocalDateTime close = LocalDateTime.of(today, ltClose); 
        
        LocalDateTime apptStart = LocalDateTime.of(ldStart, ltStart); 
        LocalDateTime apptEnd = LocalDateTime.of(ldEnd, ltEnd); 
        
        LocalDateTime utcStart = createDateToUTC(apptStart); 
        LocalDateTime utcEnd = createDateToUTC(apptEnd); 


        boolean isOpen = true; 
        if (((utcStart.getHour() <=2) || (utcStart.getHour() >= 12)) && ((utcEnd.getHour() <= 2) || (utcEnd.getHour() >=12))) {
        return isOpen;  
        } else { 
            isOpen = false;
            return isOpen; }
        
        
    }
    
}
