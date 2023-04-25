/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import country.CountryImp;
import daocustomer.CustomerImp;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;
import utils.Time;

/**
 * FXML Controller class. 
 * Allows selecting an existing customer or creating a new customer
 *
 * @author katil
 */
public class ApptSched_CustController implements Initializable {

    Stage stage; 
    Parent scene;
    int countryID = 0; 
    String country = ""; 
    private static Customer selectedCust = new Customer();
    private static boolean existingCustomer;
    private static int custID; 

    
     @FXML
    private TableView<Customer> searchCustTbl;

    @FXML
    private TableColumn<Customer, Integer> CustIDCol;

    @FXML
    private TableColumn<Customer, String> custFNameCol;

    @FXML
    private TableColumn<Customer, String> custPhNumberLbl;

    @FXML
    private TableColumn<Customer, String> custAddressCol;
    
    @FXML
    private TextField custFNameTxt;

    @FXML
    private TextField custNameTxt;

    @FXML
    private TextField custPhNumberTxt;

    @FXML
    private ComboBox<String> countryCB;

    @FXML
    private ComboBox<String> firstLevelDivCB;

    @FXML
    private TextField custAddressTxt;

    @FXML
    private TextField custCityTxt;

    @FXML
    private TextField custZipTxt;

    @FXML
    void onActionAppointments(ActionEvent event) {

    }

    /**For the next scene, to let the user know if it's an existing customer or a new customer.
     * If method returns false, the newly created customer ID is queried so that the appointment can be added. If true, the customer object is used. 
     @return True if user selected an existing customer */
    public static boolean passExistingCustomer(){
        return existingCustomer;
    }
    
    /** Passes the selected customer to the next scene. 
     @return returns the new or existing customer for use in the next scene*/
    public static Customer getSelectedCust(){
        return selectedCust;
    }
    
    /** Gets the new customer ID for use in the DB insert statement. 
     A customer ID is a foreign key restraint and required for the insert statement when creating the appointment. For new users, that has to be queried after the user is created
     @return New customer ID
     */
    public static int getNewCustID(){
    return custID; 
    } 
    
    /** On Book - for new customers, create a customer object and pass that to the DB to insert the customer. 
     For existing customers, the next scene is loaded and the selected customer is passed to that scene. 
     */
    @FXML
    void onActionBook(ActionEvent event) throws IOException {
        
        if (custNameTxt.getText().isBlank() & searchCustTbl.getSelectionModel().isEmpty()){
             Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a customer or create a new customer");
             alert.showAndWait();              
            } else if (custNameTxt.getText().isBlank() != true){
                if(custFNameTxt.getText().isBlank() || custNameTxt.getText().isBlank() || custAddressTxt.getText().isBlank() || custPhNumberTxt.getText().isBlank() || firstLevelDivCB.getValue().isBlank()){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please make sure all fields are filled in"); 
                    alert.show(); 
                    return;
                }
                existingCustomer = false;
           // create a customer object 
                String customerName = custFNameTxt.getText() + " " + custNameTxt.getText(); 
                String address = custAddressTxt.getText() + " " + custCityTxt.getText();
                String zipCode = custZipTxt.getText();
                String phone = custPhNumberTxt.getText(); 
                LocalDateTime createDate = LocalDateTime.now();  
                String createDateUTC = Time.createDateToUTC(createDate).toString(); 
                LocalDateTime lastUpdatedDate = LocalDateTime.now(); 
                String lastUpdatedDateUTC = Time.createDateToUTC(lastUpdatedDate).toString(); 
                String createdBy = "admin"; 
                String updatedBy = "admin";
                int division = CountryImp.getFirstLevelDivID(firstLevelDivCB.getValue()); 
           
                
                /*selectedCust.setFullName(customerName); 
                selectedCust.setAddress(address); 
                selectedCust.setZipCode(zipCode); 
                selectedCust.setPhoneNumber(phone);
                selectedCust.setCreateDateSt(createDateUTC);
                selectedCust.setCreatedBy(createdBy); 
                selectedCust.setLastUpdateSt(lastUpdatedDateUTC); 
                selectedCust.setLastUpdatedBy(updatedBy); 
                selectedCust.setDivision(division);*/

                custID = CustomerImp.insertCustomer(customerName, address, zipCode, phone, createDateUTC, createdBy, lastUpdatedDateUTC, updatedBy, division);
                if (custID == 0){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please make sure all fields are filled in"); 
                    alert.show(); 
                    return;
                } else {
                    stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/ApptSched_Sched.fxml")); 
                    stage.setScene(new Scene(scene));
                    stage.show();
                }    
            } else {
                existingCustomer = true; 
                selectedCust = searchCustTbl.getSelectionModel().getSelectedItem();
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/ApptSched_Sched.fxml")); 
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
    
    @FXML 
    void onActionFirstLevelDiv(ActionEvent event){

    }
    
    /**Changes the first level division as the country changes. 
     The first level division is cleared and then set to correct first level divisions based on the current selected country*/
    @FXML
    void onActionCountry(ActionEvent event){
        country = countryCB.getValue(); 
        int countryID = CountryImp.getCountryID(country); 
        firstLevelDivCB.getSelectionModel().clearSelection();
        firstLevelDivCB.getItems().clear();
        for (FirstLevelDivision fld : CountryImp.getFirstLevelDivs(countryID)){
            firstLevelDivCB.getItems().add(fld.getDivision()); 
        }    
    }
    
    
     /**
     * Initializes the controller class. 
     * Sets the values for the combo boxes and sets the table with existing customers
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ObservableList<Customer>allCustomers = FXCollections.observableArrayList();
        allCustomers = CustomerImp.getAllCustomers(); 
          
        CustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        custFNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        custPhNumberLbl.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        custAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        
        searchCustTbl.setItems(allCustomers);
          
        for (Country country : CountryImp.getCountries()){
            countryCB.getItems().add(country.getCountry());      
       }
        for (FirstLevelDivision fld : CountryImp.getFirstLevelDivs(231)){
            firstLevelDivCB.getItems().add(fld.getDivision()); 
          }  
    }
}


