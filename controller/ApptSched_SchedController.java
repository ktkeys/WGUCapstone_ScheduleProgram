/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import daoappts.ApptImp;
import controller.Login_MenuController;
import country.CountryImp;
import daocontacts.ContactImp;
import daouser.UserImp;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointments;
import model.Contact;
import model.Customer;
import model.User;
import utils.Time;



/**
 * FXML Controller class. 
 * This class handles scheduling the appointment details such as time, date, type, Contact ID, User ID
 *
 * @author katil
 */
public class ApptSched_SchedController implements Initializable {
    
    String apptType;  
    private static int userID; 
    int contactID; 
    ZoneId usersZid; 
    int custID;
    private static int apptID; 
    Customer customer = new Customer(); 
    Stage stage; 
    Parent scene;
    ObservableList<Integer> hour = FXCollections.observableArrayList(); 
    ObservableList<Integer> minute = FXCollections.observableArrayList();

    @FXML
    private Label custFNameLbl;

    @FXML
    private Label custLNameLbl;

    @FXML
    private TextField apptTitleTxt;

    @FXML
    private ComboBox<String> contactCB;
           
    @FXML
    private ComboBox<Integer> userIDCB;

    @FXML
    private TextArea apptDescTxt;

    @FXML
    private TextField apptLocationTxt;

    @FXML
    private Spinner<Integer> startHourSP;

    @FXML
    private Spinner<Integer> startMinSP;

    @FXML
    private Spinner<Integer> endHourSP;

    @FXML
    private Spinner<Integer> endMinSP;
    
    @FXML
    private DatePicker startDateDP;

    @FXML
    private DatePicker endDateDP;
     
    @FXML
    private TextField typeTxt;

    @FXML
    void onActionAppointments(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Appt_Menu.fxml")); 
        stage.setScene(new Scene(scene));
        stage.show(); 
    }

    @FXML
    void onActionCustomers(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Customer_Menu.fxml")); 
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionHome(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Home.fxml")); 
        stage.setScene(new Scene(scene));
        stage.show();
    }
    

    @FXML
    void onActionLogout(ActionEvent event) {
        System.exit(0);     

    }

    @FXML
    void onActionReports(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Report_Menu.fxml")); 
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionSystem(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/System_Menu.fxml")); 
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    /**Allows the appointment ID to be passed to other scenes. 
     The appointment ID is needed in other scenes and this method allows it to be passed. 
     @return Returns the appointment ID*/
    public static Integer passApptID () {
    return apptID; 
    }
    
    /**When the book button is pressed, appointment details are passed to the DBQuery class.
     Appointment is created and times are converted to UTC.  Various checks are done to ensure times are within business hours and make sense logically. */
    @FXML
    void onActionBook(ActionEvent event) throws IOException {

        String title = apptTitleTxt.getText(); 
        String description = apptDescTxt.getText();
        String location = apptLocationTxt.getText(); 
        LocalDate startDate = startDateDP.getValue(); 
        LocalDate endDate = endDateDP.getValue();
        
        // stripping out the name from the contact and converting to an int for db 
        int contact = Integer.parseInt(contactCB.getValue().replaceAll("[^\\d]", ""));

        String startTime = startHourSP.getValue() + ":" + startMinSP.getValue()+ ":00"; 
        String endTime = endHourSP.getValue() + ":" + endMinSP.getValue() + ":00"; 
        LocalTime ltStartTime = LocalTime.of(startHourSP.getValue(), startMinSP.getValue(), 00);
        LocalTime ltEndTime = LocalTime.of(endHourSP.getValue(), endMinSP.getValue(), 00);
        String updatedBy = "admin"; 
        String createdBy = "admin"; 

        // converting times to UTC 
        LocalDateTime ldtStart = LocalDateTime.of(startDate, ltStartTime); 
        ZonedDateTime zdtStart = ldtStart.atZone(usersZid); 
        ZonedDateTime utcStart = zdtStart.withZoneSameInstant(ZoneId.of("UTC")); 
        ldtStart = utcStart.toLocalDateTime(); 
        String startDateTimeUTC = ldtStart.toLocalDate() + " " + ldtStart.toLocalTime(); 
        
        LocalDateTime ldtEnd = LocalDateTime.of(endDate, ltEndTime); 
        ZonedDateTime zdtEnd = ldtEnd.atZone(usersZid); 
        ZonedDateTime utcEnd = zdtEnd.withZoneSameInstant(ZoneId.of("UTC")); 
        ldtEnd = utcEnd.toLocalDateTime(); 
        String endDateTimeUTC = ldtEnd.toLocalDate() + " " + ldtEnd.toLocalTime();
        
        LocalDateTime createDateTime = LocalDateTime.now();
        ZonedDateTime zdtCreate = createDateTime.atZone(usersZid); 
        ZonedDateTime utcCreate = zdtCreate.withZoneSameInstant(ZoneId.of("UTC")); 
        createDateTime = utcCreate.toLocalDateTime(); 
        String createDateTimeUTC = createDateTime.toLocalDate() + " " +  createDateTime.toLocalTime(); 
 
        LocalDateTime lastUpdateDateTime = LocalDateTime.now(); 
        ZonedDateTime zdtUpdate = lastUpdateDateTime.atZone(usersZid); 
        ZonedDateTime utcUpdate = zdtUpdate.withZoneSameInstant(ZoneId.of("UTC")); 
        lastUpdateDateTime = utcStart.toLocalDateTime();
        String lastUpdateDateTimeUTC = lastUpdateDateTime.toLocalDate() + " " + createDateTime.toLocalTime(); 

       // verifying end date is after start date 
       if (endDate.isBefore(startDate)){
        Alert alert = new Alert(Alert.AlertType.ERROR, "Please make sure the End Date is on or after the Start Date."); 
           alert.show();
           return;
        }
        
        if (startDate.isBefore(LocalDate.now())){
           Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment cannot be scheduled in the past."); 
           alert.show();
           return;
      }
        
        if (endHourSP.getValue() < startHourSP.getValue()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "End Time must be after Start Time."); 
           alert.show();
           return;
        }
        
       /*check business hours 
       if (Time.businessHours(ltStartTime, ltEndTime, startDate, endDate)){
       } else {
            return;
       }*/
 
        
       if (Time.isOverlapping(zdtStart.toLocalTime(), zdtEnd.toLocalTime(), custID, startDate, 0)){
           Alert alert = new Alert(Alert.AlertType.ERROR, "This customer already has an appointment at this time"); 
           alert.show();   
       }else{  
        apptID = ApptImp.createAppointment(title, description, location, apptType, startDateTimeUTC, endDateTimeUTC, createDateTimeUTC, createdBy, lastUpdateDateTimeUTC, updatedBy, custID,   userID, contact); 
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ApptSched_Conf.fxml")); 
        stage.setScene(new Scene(scene));
        stage.show();
        }
    }
    
    @FXML
    void onActionGoHome(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Home.fxml")); 
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
 

     /**
     * Initializes the controller class for scheduling the appointment. 
     * On initialization, the time spinners are created and set with values. The combo boxes for contacts and user IDs are populated. 
     * User ID is set to the default of the logged in users.
     * the previously selected customer is passed along with the customer iD 
     * appointment type is passed from previous scenes
     * Contains LAMBDA expression
     * 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
  
        hour.addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23); 
        minute.addAll(0, 15, 30, 45);
        
        SpinnerValueFactory<Integer> valueFactoryStartHr = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(hour); 
        SpinnerValueFactory<Integer> valueFactoryStartMin = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(minute); 
        SpinnerValueFactory<Integer> valueFactoryEndHr = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(hour); 
        SpinnerValueFactory<Integer> valueFactoryEndMin = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(minute); 
        
        startHourSP.setValueFactory(valueFactoryStartHr);        
        startMinSP.setValueFactory(valueFactoryStartMin);
        endMinSP.setValueFactory(valueFactoryEndMin);
        endHourSP.setValueFactory(valueFactoryEndHr);
        
        //LAMBDA loops through all contacts and for each gets the Contact name and ID to populate the combox. 
        ContactImp.getAllContacts().forEach((contacts) -> {
            contactCB.getItems().add(contacts.getContactName() +" " +  Integer.toString(contacts.getContactID()));
        });
       
        //LAMBDA loops through all users and for each gets the user ID to populate the combox. 
        UserImp.getAllUser().forEach((user) -> {
            userIDCB.getItems().add(user.getUserID());
        });
        
       userID = Login_MenuController.passUserID();
        userIDCB.setValue(userID); 
       
        boolean existingCustomer = ApptSched_CustController.passExistingCustomer(); 
        customer = ApptSched_CustController.getSelectedCust();
            if (existingCustomer){
                custID = customer.getCustomerID(); 
            } else {
                custID = ApptSched_CustController.getNewCustID();
            }
       
        custFNameLbl.setText(customer.getFullName());
 
        usersZid = ZoneId.systemDefault(); 
      
        // Get appointment type from the home controller 
        apptType = HomeController.getApptType(); 
        typeTxt.setText(apptType);
        

        

        

        
        
        
        
        
        
        
        
    }    
    
}
