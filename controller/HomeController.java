/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import daoappts.ApptImp;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;


/**
 * FXML Controller class for the home screen. 
 * The home screen allows a user to start booking an appointment by selecting the type or to navigate to other menus. 
 *
 * @author katil
 */
public class HomeController implements Initializable {
  
    Stage stage; 
    Parent scene;
    private static String apptType = "DEFAULT"; 

    @FXML
    private TableView<Appointments> userApptTbl;

    @FXML
    private TableColumn<Appointments, Integer> apptIDCol;

    @FXML
    private TableColumn<Appointments, String> apptTitleCol;

    @FXML
    private TableColumn<Appointments, LocalDate> startDateCol;

    @FXML
    private TableColumn<Appointments, LocalTime> startTimeCol;

    @FXML
    private TableColumn<Appointments, LocalTime> endTimeCol;

    @FXML
    private TableColumn<Appointments, String> apptTypeCol;

    @FXML
    private TableColumn<Appointments, Integer> apptContactCol;
    
    @FXML
    private ListView<String> apptAlertlv;
    
    @FXML
    private Label apptAlertListLbl;

    @FXML
    void onActionAppointments(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Appt_Menu.fxml")); 
        stage.setScene(new Scene(scene));
        stage.show(); 
    }

    @FXML
    void onActionBookDJ(ActionEvent event) throws IOException {
        apptType = "DJ"; 
        setStage(event); 
    }

    @FXML
    void onActionBookGroup(ActionEvent event) throws IOException {
        apptType = "Group"; 
        setStage(event); 
    }

    @FXML
    void onActionBookOther(ActionEvent event) throws IOException {
        apptType = "Other:"; 
        setStage(event); 
    }

    @FXML
    void onActionBookPiano(ActionEvent event) throws IOException {
        apptType = "Piano"; 
        setStage(event); 
    }

    @FXML
    void onActionBookSing(ActionEvent event) throws IOException {
        apptType = "Sing"; 
        setStage(event); 
    }

    @FXML
    void onActionBookUkelele(ActionEvent event) throws IOException {
        apptType = "Ukelele"; 
        setStage(event); 
    }

    @FXML
    void onActionBookWriting(ActionEvent event) throws IOException {
        apptType = "Writing"; 
        setStage(event); 
    }
    

    @FXML
    void onActionCustomers(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Customer_Menu.fxml")); 
        stage.setScene(new Scene(scene));
        stage.show(); 
    }

    @FXML
    void onActionHome(ActionEvent event) {
        //nothing happens here when you're home and hit home 
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
    
    @FXML
    void onActionContacts(ActionEvent event) throws IOException{
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Contacts_Menu.fxml")); 
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    /** * Passes the selected appointment type.This method will pass the selected appointment type for use in later scenes
     * @return */
    public static String getApptType(){
        return apptType; 
    }
    
    
   /**
     * Initializes the controller class for the home menu.Checks to see if there are any appointments with 15 minutes of the user logging in and will display a message if there is.
     * @param url 
     * @param rb 
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Appointments>todaysAppts = FXCollections.observableArrayList();
        todaysAppts = ApptImp.getTodaysAppts();
        LocalTime currentTime = LocalTime.now(); 
        ObservableList<Appointments>approachingAppts = FXCollections.observableArrayList(); 
        // check if anything starts within 15 minutes 
        for (Appointments appt : ApptImp.getTodaysAppts()){
            Duration apptAlert = Duration.between(appt.getLtStartTime(), currentTime);
            Long apptDiff = apptAlert.toMinutes(); 
            if (apptDiff < 15) {
                approachingAppts.add(appt);  
            }  
        }
        
        if (approachingAppts.isEmpty()){
           apptAlertlv.getItems().add("You have no upcoming appointments");
        } else {
            apptAlertlv.getItems().add("You have the following upcoming appointments:" ); 
            for (Appointments appt : approachingAppts){
                String apptToAdd = "Appointment ID: " + appt.getApptID() + "  Start Time: " + appt.getLtStartTime() + "  Date: " + appt.getLdStartDate();
                apptAlertlv.getItems().add(apptToAdd); 
             }     
        }
        
        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("ldStartDate"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("ltStartTime")); 
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("ltEndTime"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type")); 
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contactID")); 
        
        userApptTbl.setItems(todaysAppts);
   
    }    
    
    /**Sets the stage after selecting an appointment type.
     * @param event
     * @throws java.io.IOException
     */ 
    public void setStage(ActionEvent event) throws IOException{
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ApptSched_Cust.fxml")); 
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
}
