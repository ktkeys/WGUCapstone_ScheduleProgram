package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import daoappts.ApptImp;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;

/**
 * FXML Controller class that display all appointments and allows users to select and modify. 
 * 
 *
 * @author katil
 */
public class Appt_MenuController implements Initializable {
  
    Stage stage; 
    Parent scene; 
    ObservableList<Appointments>allAppts = FXCollections.observableArrayList();
    ObservableList<Appointments> weeklyAppt = FXCollections.observableArrayList();
    ObservableList<Appointments> monthlyAppt = FXCollections.observableArrayList();
        ObservableList<Appointments> searchAppt = FXCollections.observableArrayList();

    



    
    @FXML
    private TableView<Appointments> apptTbl;

    @FXML
    private TableColumn<Appointments, Integer> apptIDCol;

    @FXML
    private TableColumn<Appointments, String> apptTitleCol;

    @FXML
    private TableColumn<Appointments, String> apptDescCol;

    @FXML
    private TableColumn<Appointments, String> apptLocCol;

    @FXML
    private TableColumn<Appointments, String> apptContactCol;

    @FXML
    private TableColumn<Appointments, String> apptTypeCol;

    @FXML
    private TableColumn<Appointments, LocalDate> apptStartDateCol;

    @FXML
    private TableColumn<Appointments, LocalTime> apptStartTimeCol;

    @FXML
    private TableColumn<Appointments, LocalDate> apptEndDateCol;

    @FXML
    private TableColumn<Appointments, LocalTime> apptEndTimeCol;

    @FXML
    private TableColumn<Appointments, String> apptCustIDCol;

    @FXML
    private ToggleGroup viewByTG;
    
    @FXML
    private TextField searchTxt; 
    
    @FXML
    private RadioButton weeklyRb; 
    
    @FXML RadioButton monthlyRb; 

    @FXML
    void onActionAppointments(ActionEvent event) {

    }

    /**Allows modification to the select appointment in the table. 
     Checks to see if an appointment is selected and then brings the user to the appointment modification scene. */
    @FXML
    void onActionApptModify(ActionEvent event) throws IOException {
        Appointments selectedAppt = new Appointments(); 
       
        if (apptTbl.getSelectionModel().getSelectedItem() == null ){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select an appointment from the table"); 
            alert.show(); 
        }else {
            selectedAppt = apptTbl.getSelectionModel().getSelectedItem(); 
            Appt_ModController.passAppointment(selectedAppt); 
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Appt_Mod.fxml")); 
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    
    @FXML
    void onActionNew(ActionEvent event) throws IOException {
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

    /**Radio button for viewing appointments scheduled this month. 
     Clears the list first so duplicates are not displayed. Then checks to see if the appointment date month is the same as this month*/
    @FXML
    void onActionViewByMonth(ActionEvent event) {
        monthlyAppt.clear();
        LocalDate todaysDate = LocalDate.now(); 
        for (Appointments appt : allAppts){
            LocalDate apptDate = appt.getLdStartDate(); 
            if (apptDate.getMonth() == todaysDate.getMonth()){   
                monthlyAppt.add(appt); 
            }
        }
        apptTbl.setItems(monthlyAppt);
    }

    /**Radio button for viewing appointments scheduled this week. 
     Clears the list first so duplicates are not displayed. Then checks to see if the appointment is within a 7 day range*/
    @FXML
    void onActionViewByWeek(ActionEvent event) {
        weeklyAppt.clear();
        LocalDate todaysDate = LocalDate.now(); 
        for (Appointments appt : allAppts){
            LocalDate apptDate = appt.getLdStartDate(); 
            if (((apptDate.isAfter(todaysDate))|| apptDate.isEqual(todaysDate)) && (apptDate.isBefore(todaysDate.plusDays(7)))){
                weeklyAppt.add(appt); 
            }
        }
        apptTbl.setItems(weeklyAppt);
    }
    
      @FXML
    void onActionSearch(ActionEvent event) {
        searchAppt.clear();
        String searchTerm = searchTxt.getText();
        for (Appointments appt : allAppts){
            
            if ((appt.getDescription().contains(searchTerm)) || appt.getTitle().contains(searchTerm)){
                searchAppt.add(appt); 
            }

    }
        apptTbl.setItems(searchAppt);
    }
    
    /**Clears the weekly or monthly view. */
    @FXML
    void onActionClear(ActionEvent event) {
        apptTbl.setItems(allAppts); 
        weeklyRb.setSelected(false); 
        monthlyRb.setSelected(false);     
    }

    /**
     * Initializes the controller class that displays all appointments. 
     * Sets the table to all appointments 
     */    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

      allAppts = ApptImp.getAllAppts(); 
      apptIDCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
      apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
      apptDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
      apptLocCol.setCellValueFactory(new PropertyValueFactory<>("location"));
      apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
      apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
      apptStartDateCol.setCellValueFactory(new PropertyValueFactory<>("ldStartDate"));
      apptEndDateCol.setCellValueFactory(new PropertyValueFactory<>("ldEndDate"));
      apptEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("ltEndTime"));
      apptStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("ltStartTime"));
      apptCustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
      apptTbl.setItems(allAppts);
    }    
    
}
