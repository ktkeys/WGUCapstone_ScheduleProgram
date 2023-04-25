/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import country.CountryImp;
import daoappts.ApptImp;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.ListSelectionModel;
import model.Appointments;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;
import utils.Time;

/**
 * FXML Controller class that allows for management of customers
 *
 * @author katil
 */
public class Customer_MenuController implements Initializable { 
    Stage stage; 
    Parent scene; 
    Customer selectedCust = new Customer(); 
    ObservableList<Appointments>custAppts = FXCollections.observableArrayList();
    int custID; 
    ObservableList<Customer>allCustomers = FXCollections.observableArrayList();
    String country = "";
    ObservableList<Customer> searchCust = FXCollections.observableArrayList();


    @FXML
    private TableView<Customer> customerTbl;

    @FXML
    private TableColumn<Customer, Integer> customerIDCol;

    @FXML
    private TableColumn<Customer, String> custFNameCol;

    @FXML
    private TableColumn<Customer, Integer> custPhNumberCol;

    @FXML
    private TableColumn<Customer, String> custAddressCol;
    
    @FXML
    private ComboBox<String> countryCB;

    @FXML
    private ComboBox<String> stateCB;

    @FXML
    private TextField custFNameTxt;

    @FXML
    private TextField CustPhNumberTxt;

    @FXML
    private TextField custAddressTxt;

    @FXML
    private TextField custZipTxt;

    @FXML
    private TableView<Appointments> custApptsTbl;

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
    private TableColumn<Appointments, String> fldCol;

    @FXML
    private TableColumn<Appointments, String> apptTypeCol;

    @FXML
    private TableColumn<Appointments, Integer> apptContactCol;
    
        @FXML
    private TextField searchTxt; 
    
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

    /**Deletes an appointment. 
     Based off the selected item in the table, will offer a confirmation box and then delete the appointment*/
    @FXML
    void onActionDeleteAppt(ActionEvent event) {
        Appointments selectedAppt = custApptsTbl.getSelectionModel().getSelectedItem(); 
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel this appointment?"); 
        Optional <ButtonType> result = alert.showAndWait(); 
        if (result.isPresent() && result.get() == ButtonType.OK){
            int update = ApptImp.deleteAppointment(selectedAppt.getApptID());
            if (update > 0) {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Appointment has been deleted");
                alert2.show();
            }
        }
        setAppointmentTable();
    }

    /**Deletes the selected customer. 
     Deletes the customer but will first prompt for confirmation.  Once confirmed, all associated appointments + customer will be deleted*/
    @FXML
    void onActionDeleteUser(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Deleting a customer will also delete all the customer's appointments.  Are you sure you want to proceed?"); 
        Optional <ButtonType> result = alert.showAndWait(); 
        if (result.isPresent() && result.get() == ButtonType.OK){
            int update = CustomerImp.deleteCustomer(custID);
            if (update > 0) {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Appointment has been deleted");
                alert2.show();
            } 

        // reset table with current info 
        setCustomerTable();
        setAppointmentTable(); 
        }
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

    /**Saves edited customer information. 
     */
    @FXML
    void onActionSaveCust(ActionEvent event) {

        String name = custFNameTxt.getText(); 
        String phone = CustPhNumberTxt.getText(); 
        String address = custAddressTxt.getText(); 
        String zipCode = custZipTxt.getText(); 
        int division = CountryImp.getFirstLevelDivID(stateCB.getValue()); 
        int update = CustomerImp.updateCustomer(custID, name, phone, address, zipCode, division); 
        if (update > 0) {
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Record Updated");
            alert2.show();
        }
        // reset table with current info 
        setCustomerTable(); 
        setAppointmentTable(); 
    }
    
    @FXML
    void onActionAddCust(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ApptSched_Cust.fxml")); 
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
    void onActionSystem(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/System_Menu.fxml")); 
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**Updates the appointments. 
     Will pass the selected appointment to the next scene for modification*/
    @FXML
    void onActionUpdateAppt(ActionEvent event) throws IOException {
        Appointments selectedAppt = custApptsTbl.getSelectionModel().getSelectedItem(); 
        Appt_ModController.passAppointment(selectedAppt); 
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Appt_Mod.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();        
    }
    /**Updates the first level division based on the country selection. 
     Based on the country selection the combo box for the first level division will update properly*/
    @FXML
    void onActionCountry(ActionEvent event) {
        country = countryCB.getValue(); 
        int countryID = CountryImp.getCountryID(country); 
        stateCB.getSelectionModel().clearSelection();
        stateCB.getItems().clear();
        for (FirstLevelDivision fld : CountryImp.getFirstLevelDivs(countryID)){
            stateCB.getItems().add(fld.getDivision()); 
    }    

    }
    
        @FXML
    void onActionSearch(ActionEvent event) {
        searchCust.clear(); 
        String searchTerm = searchTxt.getText(); 
        for(Customer cust : allCustomers) {
        if (cust.getFullName().contains(searchTerm))
            searchCust.add(cust); 
        }
        
        customerTbl.setItems(searchCust);

    }
    
        @FXML
    void onActionClearSearch(ActionEvent event) {
        customerTbl.setItems(allCustomers);
        searchCust.clear();
            
    }

    

    /**Sets the customer fields based of the table selection.
     This will also set the appointment table based off the selected customer*/ 
    public void setCustomerData(){
        selectedCust = customerTbl.getSelectionModel().getSelectedItem();
        custID = selectedCust.getCustomerID(); 
        custFNameTxt.setText(selectedCust.getFullName());
        CustPhNumberTxt.setText(selectedCust.getPhoneNumber());
        custAddressTxt.setText(selectedCust.getAddress());
        custZipTxt.setText(selectedCust.getZipCode());
        countryCB.setValue(selectedCust.getCountryName());
        stateCB.setValue(selectedCust.getDivisionName());
                  
        setAppointmentTable(); 
    }
    
    /**Sets the appointment table. 
     Sets the appointment table based of the selected customer*/ 
    public void setAppointmentTable(){
        custAppts = ApptImp.getCustAppt(selectedCust.getCustomerID()); 
        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("ldStartDate"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("ltStartTime"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("ltEndTime"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contactID")); 
        custApptsTbl.setItems(custAppts); 
    }
    
    /**Sets the customer table with all customer records. 
     Sets the customer table with all customer records. */
    public void setCustomerTable() { 
        allCustomers = CustomerImp.getAllCustomers();   
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        custFNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        custPhNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        custAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        fldCol.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        customerTbl.setItems(allCustomers);
}

    
    /**
     * Initializes the controller class to manage customers. 
     * Watches for mouse clicks on the table to update text fields based on what is selected in the table. 
     * Uses lambda expression for more efficient code
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       //lambda expression to listen for when the table is clicked, if it's the primary button, the contact data is set to the new value
        setCustomerTable(); 
        customerTbl.setOnMouseClicked((MouseEvent me) -> {
        if (me.getButton().equals(MouseButton.PRIMARY)){
            setCustomerData();  
        }
        });
        
        //LAMBDA loops through all countries and for each gets the country name to populate the combox. 
        CountryImp.getCountries().forEach((country) -> {
            countryCB.getItems().add(country.getCountry());
        });
          
        //LAMBDA loops through all first level divisions and for each gets the name to populate the combox. 
        CountryImp.getFirstLevelDivs(231).forEach((fld) -> {
            stateCB.getItems().add(fld.getDivision());
        });      
          
          
          
          
        
        

        
        
        
     
          
          
      
    }    
    
    
    
    
}
