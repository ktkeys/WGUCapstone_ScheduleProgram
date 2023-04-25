/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import daoappts.ApptImp;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;

/**
 * FXML Controller class for Appt by Contact Report. 
 * This class handles the controller for the report displaying appointments by Contact
 * @author katil
 */


public class ApptByContact_Controller implements Initializable {
    
    Stage stage; 
    Parent scene;

    @FXML
    private TableView<Appointments> typeMonthTbl;
    
    @FXML
    private TableColumn<Appointments, Integer> contactIDCol;

    @FXML
    private TableColumn<Appointments, String> contactNameCol;

    @FXML
    private TableColumn<Appointments, Integer> apptIDCol;

    @FXML
    private TableColumn<Appointments, String> titleCol;

    @FXML
    private TableColumn<Appointments, String> typeCol;

    @FXML
    private TableColumn<Appointments, String> descriptionCol;

    @FXML
    private TableColumn<Appointments, LocalDate> startDateCol;

    @FXML
    private TableColumn<Appointments, LocalTime> startTimeCol;

    @FXML
    private TableColumn<Appointments, LocalDate> endDateCol;

    @FXML
    private TableColumn<Appointments, LocalTime> endTimeCol;

    @FXML
    private TableColumn<Appointments, Integer> customerIDCol;
    
    @FXML
    void onActionBack(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Report_Menu.fxml")); 
        stage.setScene(new Scene(scene));
        stage.show();
        

    }


    /**
     * Initializes the controller class. 
     * This sets the appointment table for the scene using an ObservableList called by a DB Query 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

       ObservableList<Appointments> contactAppts = FXCollections.observableArrayList(); 
       contactAppts = ApptImp.getContactAppt(); 
       
      contactIDCol.setCellValueFactory(new PropertyValueFactory<>("contactID")); 
      contactNameCol.setCellValueFactory(new PropertyValueFactory<>("contactName")); 
      apptIDCol.setCellValueFactory(new PropertyValueFactory<>("apptID")); 
      titleCol.setCellValueFactory(new PropertyValueFactory<>("title")); 
      typeCol.setCellValueFactory(new PropertyValueFactory<>("type")); 
      descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description")); 
      startDateCol.setCellValueFactory(new PropertyValueFactory<>("ldStartDate")); 
      startTimeCol.setCellValueFactory(new PropertyValueFactory<>("ltStartTime")); 
      endDateCol.setCellValueFactory(new PropertyValueFactory<>("ldEndDate")); 
      endTimeCol.setCellValueFactory(new PropertyValueFactory<>("ltEndTime")); 
      customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID")); 
       
      typeMonthTbl.setItems(contactAppts);

       
        
    }    
    
}
