/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import daoappts.ApptImp;
import daocontacts.ContactImp;
import daocustomer.CustomerImp;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.ButtonType;
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
import utils.Time;

/**
 * FXML Controller class to modify or cancel appointments 
 *
 * @author katil
 */
public class Appt_ModController implements Initializable {

    Stage stage; 
    Parent scene; 
    private static Appointments modAppt = null; 
    
    @FXML
    private Button onActionApptSave;
   
    @FXML
    private Label ApptIDLbl;

    @FXML
    private DatePicker startDateDP;

    @FXML
    private DatePicker apptEndDateDP;
 
    @FXML
    private Spinner<Integer> startHrTimeSpin;

    @FXML
    private Spinner<Integer> startMinTimeSpin;

    @FXML
    private Spinner<Integer> endHrTimeSpin;

    @FXML
    private Spinner<Integer> endMinTimeSpin;

    @FXML
    private TextField apptTitleTxt;

    @FXML
    private TextArea apptDescTxt;

    @FXML
    private TextField apptLocationTxt;

    @FXML
    private TextField custPhNumberTxt;

    @FXML
    private TextField customerEmailTxt;

    @FXML
    private Label apptCustomerLbl;

    @FXML
    private Label apptTypeLbl;

    @FXML
    private Label apptFNameLbl;

    @FXML
    private Label apptLNameLbl;
    
    @FXML
    private ComboBox<String> contactCb;
        
    @FXML
    private TextField typeTxt;

    @FXML
    void onActionAppointments(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Appt_Menu.fxml")); 
        stage.setScene(new Scene(scene));
        stage.show(); 
    }

    /**Cancels appointments. 
     Prompts the user to cancel appointments prior to deleting from the DB*/
    @FXML
    void onActionApptCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel Appointment ID: " + modAppt.getApptID() + " of Type: " + modAppt.getType());  
        Optional <ButtonType> result = alert.showAndWait(); 
        if (result.isPresent() && result.get() == ButtonType.OK){
            int update = ApptImp.deleteAppointment(modAppt.getApptID());
            if (update > 0){    
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "Appointment has been deleted.");
                alert2.show();
            }
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Appt_Menu.fxml")); 
        stage.setScene(new Scene(scene));
        stage.show();
        }    
    }

    @FXML
    void onActionCustomers(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Customer_Menu.fxml")); 
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionGoHome(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Home.fxml")); 
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    @FXML
    void onActionContacts(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Contacts_Menu.fxml")); 
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
    
   /**If the update button is pressed, the changes are sent to the DB for update the record.
    The same checks and balances are done to ensure that all dates are set logically */ 
   @FXML
   void onActionUpdateAppt(ActionEvent event) throws IOException{
       int apptID = modAppt.getApptID(); 
       String type = typeTxt.getText(); 
       LocalDate startDate = startDateDP.getValue(); 
       LocalTime startTime = LocalTime.of(startHrTimeSpin.getValue(), startMinTimeSpin.getValue()); 
       LocalDate endDate = apptEndDateDP.getValue(); 
       LocalTime endTime = LocalTime.of(endHrTimeSpin.getValue(), endMinTimeSpin.getValue());
       String title = apptTitleTxt.getText(); 
       String description = apptDescTxt.getText(); 
       int contactID = Integer.parseInt(contactCb.getValue().replaceAll("[^\\d]", "")); 
       String location = apptLocationTxt.getText(); 
  
      if (endDate.isBefore(startDate)){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please make sure the end date is on or after the start date."); 
            alert.show();
           return;
      }
      
      if (startDate.isBefore(LocalDate.now())){
           Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment cannot be scheduled in the past."); 
           alert.show();
           return;
      }
      
      if (endHrTimeSpin.getValue() < startHrTimeSpin.getValue()) {
           Alert alert = new Alert(Alert.AlertType.ERROR, "End Time must be after Start Time."); 
           alert.show();
           return; 
      }
       
       //check business hours 
       if (Time.businessHours(startTime, endTime, startDate, endDate)){
       }else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Business Hours are from 8:00 AM EST to 10 PM EST.  Pleas schedule the appointment within business hours"); 
            alert.show(); 
            return;
       }
        //check if overlapping 
       if (Time.isOverlapping(startTime, endTime, modAppt.getCustomerID(), startDate, apptID)){
           Alert alert = new Alert(Alert.AlertType.ERROR, "This customer already has an appointment at this time"); 
           alert.show();           
        } else {
            int update = ApptImp.updateAppointment(apptID, type, startDate, endDate, startTime , endTime, title, description, contactID, location);
                if (update >= 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Appointment has been updated"); 
                    alert.show();
                    stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/Appt_Menu.fxml")); 
                    stage.setScene(new Scene(scene));
                    stage.show();     
                }
            }
   }
   
    
    /**Allows an appointment to be passed to the modification scene.
     * @param appt */
   public static void passAppointment(Appointments appt){
        modAppt = appt; 
    }
    
     /**
     * Initializes the controller class for modifying appointments. Text fields are set to the values retrieved from the appointment object 
 Combo boxes are populated with values  
 * Contains lambda expression
     *
     * @param url 
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        String custName = CustomerImp.getCustName(modAppt.getCustomerID());
        apptFNameLbl.setText(custName + "(" + modAppt.getCustomerID() + ")"); 
        ApptIDLbl.setText(Integer.toString(modAppt.getApptID())); 
        startDateDP.setValue(modAppt.getLdStartDate());
        apptEndDateDP.setValue(modAppt.getLdEndDate());
        apptTitleTxt.setText(modAppt.getTitle());
        apptDescTxt.setText(modAppt.getDescription());
        apptLocationTxt.setText(modAppt.getLocation());
        typeTxt.setText(modAppt.getType());
        
        //LAMBDA loops through all contacts and for each gets the Contact name and ID to populate the combox. 
        ContactImp.getAllContacts().forEach((contacts) -> {
            contactCb.getItems().add(contacts.getContactName() +" " +  Integer.toString(contacts.getContactID()));
        });
        
        // create the appt contactfor the combobox 
       int contactID = modAppt.getContactID(); 
       String contactName = ContactImp.getContactName(contactID); 
       contactCb.setValue(contactName);
       
       int startHr = modAppt.getLtStartTime().getHour(); 
       int startMin = modAppt.getLtStartTime().getMinute(); 
       int endHr = modAppt.getLtEndTime().getHour(); 
       int endMin = modAppt.getLtEndTime().getMinute(); 
 
        ObservableList<Integer> hour = FXCollections.observableArrayList(); 
        ObservableList<Integer> minute = FXCollections.observableArrayList();
        hour.addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23); 
         
        minute.addAll(0, 15, 30, 45);
        SpinnerValueFactory<Integer> valueFactoryStartHr = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(hour); 
        SpinnerValueFactory<Integer> valueFactoryStartMin = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(minute); 
        SpinnerValueFactory<Integer> valueFactoryEndHr = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(hour); 
        SpinnerValueFactory<Integer> valueFactoryEndMin = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(minute);
       
        startHrTimeSpin.setValueFactory(valueFactoryStartHr);        
        startMinTimeSpin.setValueFactory(valueFactoryStartMin);
        endHrTimeSpin.setValueFactory(valueFactoryEndHr);
        endMinTimeSpin.setValueFactory(valueFactoryEndMin);
        
        startHrTimeSpin.getValueFactory().setValue(startHr);
        startMinTimeSpin.getValueFactory().setValue(startMin);
        endHrTimeSpin.getValueFactory().setValue(endHr);
        endMinTimeSpin.getValueFactory().setValue(endMin);   
    }    
    
}
