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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Appointments;
import model.Contact;
import model.Customer;

/**
 * FXML Controller class to manage contacts
 *
 * @author katil
 */



public class Contact_Controller implements Initializable {
    
    Stage stage; 
    Parent scene; 
    ObservableList<Contact>allContacts = FXCollections.observableArrayList();
    ObservableList<Appointments>contactAppts = FXCollections.observableArrayList();
    Contact selectedContact = new Contact(); 
    int contactID; 
    ObservableList<Contact> searchContact = FXCollections.observableArrayList();


    @FXML
    private TableView<Contact> contactTbl;
    
    @FXML
    private TableColumn<Contact, Integer> contactIDCol;
    
    @FXML
    private TableColumn<Contact, String> contactNameCol;
    
    @FXML
    private TableColumn<Contact, String> contactEmailCol;
    
    @FXML
    private Label contactIDLbl;
    
    @FXML
    private TextField emailTxt;

    @FXML
    private TextField contactNameTxt;
    
    @FXML
    private TableView<Appointments> contactAppTbl;
    
    @FXML
    private TableColumn<Appointments, Integer> apptIDCol;
    
    @FXML
    private TableColumn<Appointments, String> apptTitleCol;
   
    @FXML
    private TableColumn<Appointments, String> apptTypeCol;
    
    @FXML
    private TextField searchTxt; 
 
    @FXML
    private void onActionGoHome(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Home.fxml")); 
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionUpdateContact(ActionEvent event) {
        String name = contactNameTxt.getText(); 
        String email = emailTxt.getText(); 
        
        int update = ContactImp.updateContact(contactID,name, email); 
        if (update > 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Contact has been updated."); 
            alert.show();           
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please review the information and ensure all fields are properly filled in."); 
            alert.show();
        }  
        // reset table with current info 
        setContactTable(); 
    }



    @FXML
    private void onActionUpdateAppt(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Appt_Mod.fxml")); 
        stage.setScene(new Scene(scene));
        stage.show();
        
    }

    @FXML
    private void onActionHome(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Home.fxml")); 
        stage.setScene(new Scene(scene));
        stage.show();  
    }

    @FXML
    private void onActionAppointments(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Appt_Menu.fxml")); 
        stage.setScene(new Scene(scene));
        stage.show(); 
    }

    @FXML
    private void onActionCustomers(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Customer_Menu.fxml")); 
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private void onActionReports(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Report_Menu.fxml")); 
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private void onActionSystem(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/System_Menu.fxml")); 
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private void onActionLogout(ActionEvent event) {
                System.exit(0);     

    }
    
    @FXML
    void onActionSearch(ActionEvent event) {
        searchContact.clear(); 
        String searchTerm = searchTxt.getText(); 
        for (Contact contact : allContacts){
            if (contact.getContactName().contains(searchTerm))
                searchContact.add(contact); 
        }
        
        contactTbl.setItems(searchContact);
    }
    
    @FXML
    void onActionDeleteUser(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Deleting a contact will also delete all the contact's appointments.  Are you sure you want to proceed?"); 
        Optional <ButtonType> result = alert.showAndWait(); 
            if (result.isPresent() && result.get() == ButtonType.OK){
                int update = ContactImp.deleteContact(selectedContact.getContactID()); 
                if (update > 0) {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Appointment has been deleted");
                alert2.show();
            } 

        // reset table with current info 
       setContactTable(); 
       setAppointmentTable();
       contactNameTxt.clear(); 
       emailTxt.clear(); 
       contactIDLbl.setText(null);
        }
    }
    
    @FXML
    void onActionClearSearch(ActionEvent event) {
        contactTbl.setItems(allContacts); 
        searchTxt.clear(); 
        contactNameTxt.clear();
        emailTxt.clear(); 
        contactIDLbl.setText(null); 
        

    }
    
    @FXML
    void onActionAddContact(ActionEvent event) {
        int contactID = 0; 
        
        //if contact id is empty - add nwe, else, update... 
        if (contactID == 0) 
            contactID = ContactImp.addContact(contactNameTxt.getText(), emailTxt.getText());
            contactIDLbl.setText(Integer.toString(contactID)); 
            setContactTable(); 

        

    }
    
    /**Sets the contact table. 
     Queries the database and sets the table */
    public void setContactTable(){
        allContacts = ContactImp.getAllContacts();  
        contactIDCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        contactNameCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        contactEmailCol.setCellValueFactory(new PropertyValueFactory<>("EMail")); 
        contactTbl.setItems(allContacts);
    }
    
    /** Sets the contact data when a contact is selected. 
     Allows the text fields to be updated and sets the appointment tables based on the selected customer*/
    public void setContactData(){
        selectedContact = contactTbl.getSelectionModel().getSelectedItem(); 
        contactID = selectedContact.getContactID();
        contactNameTxt.setText(selectedContact.getContactName()); 
        emailTxt.setText(selectedContact.getEMail()); 
        contactIDLbl.setText(Integer.toString(selectedContact.getContactID()));
        
        setAppointmentTable(); 
    }
    

    
    public void setAppointmentTable(){
        contactAppts = ApptImp.getContactAppt(contactID); 
        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        contactAppTbl.setItems(contactAppts);
    }
    
    /**
     * Initializes the controller class to manage contacts. 
     * watches for a mouse event so that when a contact is selected in the table, the contact data is set. Uses a lambda expression for more efficient code. 
     * contains lambda expressions
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       setContactTable();
       
       //lambda expression to listen for when the table is clicked, if it's the primary button, the contact data is set to the new value
        contactTbl.setOnMouseClicked((MouseEvent me) -> {
              if (me.getButton().equals(MouseButton.PRIMARY)){
              setContactData();    
              }
        });    
}
    
}

