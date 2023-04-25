/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import daoappts.ApptImp;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Appointments;
import model.Customer;

/**
 * FXML Controller class to Load the reports screen. 
 *
 * @author katil
 */
public class Report_MenuController implements Initializable {


    
    Stage stage; 
    Stage reportStage;
   Parent reportScene;
    Parent scene; 
    
    @FXML
    private RadioButton typeMonthRB;
        
    @FXML
    private RadioButton contactScheduleRB;
    
    @FXML
    private RadioButton byLocationRB;

    @FXML
    private ListView<String> report;

    @FXML
    void onActionAppointments(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Appt_Menu.fxml")); 
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
        
    }
    @FXML
    void onActionRunReport(ActionEvent event) throws IOException  {
        if (typeMonthRB.isSelected()){
            reportScene = FXMLLoader.load(getClass().getResource("/view/TypeAndMonth.fxml")); 
        }else if (contactScheduleRB.isSelected()){           
            reportScene = FXMLLoader.load(getClass().getResource("/view/ApptByContact.fxml")); 
        }else if (byLocationRB.isSelected()){
            reportScene = FXMLLoader.load(getClass().getResource("/view/ByLocation.fxml"));
       }else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a report to run"); 
            alert.show();
       }
       reportStage = (Stage)((Button)event.getSource()).getScene().getWindow();
       reportStage.setScene(new Scene(reportScene));
       reportStage.show();
    }
    
    @FXML
    void onActionSystem(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/System_Menu.fxml")); 
        stage.setScene(new Scene(scene));
        stage.show();
    }


    /**
     * Initializes the controller class for the Reports menu.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
}
