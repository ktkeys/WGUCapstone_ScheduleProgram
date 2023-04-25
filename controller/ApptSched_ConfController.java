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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Appointments;
import scheduleprogram.ScheduleProgram;


/**
 * FXML Controller class for the appointment scheduled confirmation screen
 *
 * @author katil
 */
public class ApptSched_ConfController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    Stage stage;
    Parent scene;
    
    @FXML
    private Label apptIDLbl;

    @FXML
    private Label apptTitleLbl;

    @FXML
    private Label apptDescLbl;

    @FXML
    private Label apptContactLbl;

    @FXML
    private Label apptTypeLbl;

    @FXML
    private Label customerFLNameLbl;

    @FXML
    private Label apptLocationLbl;

    @FXML
    private Label customerPhoneNumberLbl;

    @FXML
    private Label customeEmailLbl;

    @FXML
    private Label apptStartDateLbl;

    @FXML
    private Label apptStartTimeLbl;

    @FXML
    private Label apptEndDateLbl;

    @FXML
    private Label apptEndTimeLbl;

    @FXML
    void onActionAppointments(ActionEvent event) throws IOException { 
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Appt_Menu")); 
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
    void onActionGoHome(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Home.fxml")); 
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
    void onActionModifyAppt(ActionEvent event) throws IOException {
 stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Appt_Menu.fxml")); 
        stage.setScene(new Scene(scene));
        stage.show(); 
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
    
    /**
     * Initializes the controller class. 
     * passes the appointment information to text fields as a confirmation screen 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int apptID = ApptSched_SchedController.passApptID();         
        
        Appointments appt = ApptImp.getAppt(apptID); 
        int contactID = appt.getContactID(); 
        int customerID = appt.getCustomerID(); 
        
        apptIDLbl.setText(Integer.toString(appt.getApptID())); 
        apptTitleLbl.setText(appt.getTitle());
        apptDescLbl.setText(appt.getDescription()); 
        apptContactLbl.setText(ContactImp.getContactName(contactID));
        apptTypeLbl.setText(appt.getType());
        customerFLNameLbl.setText(CustomerImp.getCustName(customerID));
        apptLocationLbl.setText(appt.getLocation()); 
        customerPhoneNumberLbl.setText(CustomerImp.getCustPhone(customerID)); 
        
        
        
        
    }    
    
}
