/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import daouser.UserImp;
import java.io.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

/**
 * FXML Controller class for the Login Screen. 
 * Displays the time and the users location and timezone
 *
 * @author katil
 */
public class Login_MenuController implements Initializable {
    Stage stage; 
    Parent scene; 
    ResourceBundle rb; 
    String loginError = "The username and password combo does not exist"; 
    static User currentUser = new User();
    private static int userID;
    
    @FXML
    private TextField usernameTxt;
    
    @FXML
    private TextField passwordTxt;
    
    @FXML
    private Label loginTimeLbl;
    
    @FXML
    private Label loginLocationLbl;
    
    @FXML
    private Label welcomeLbl;

    @FXML
    private Label usernameLbl;
    
    @FXML
    private Label passwordLbl;
    
    @FXML
    private Button loginBtn;

    @FXML
    private Label timeLbl;

    @FXML
    private Label langLocLbl;


         
    @FXML
    void onActionLogin(ActionEvent event) throws IOException {
        
        String username = usernameTxt.getText(); 
        String password = passwordTxt.getText();
        String loginResult = "Success"; 

        if (UserImp.getUnPw(username, password)){
            currentUser = UserImp.getUser(username, password);
           // loginActivity(username, loginResult);
            userID = currentUser.getUserID();
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Home.fxml")); 
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, loginError); 
            alert.show();  
            loginResult = "Failed";
            //loginActivity(username, loginResult);
     }
        
    }
    
   /* public void loginActivity (String username, String result) throws IOException{
        String loginRecord;
        String filename = "%TEMP%\\login.txt"; 
        LocalDateTime loginTime = LocalDateTime.now(); 

        FileWriter fwriter = new FileWriter(filename, true); 
        PrintWriter outputFile = new PrintWriter(fwriter); 
        loginRecord = String.format("%-10s %-5s %-10s %-5s %-10s", username, "|", result, "|", loginTime.toString());
        for (int i = 0; i < 1; i++ ){
            outputFile.println(loginRecord); 
            
        }
        
        outputFile.close();
    
    }*/
 
      /**
     * Initializes the controller class for the login screen.Checks the users default zone and adjusts as needed.If users system default is French, text will be changed to french
     * @param url
     * @param rb
     */  
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.rb = rb; 
        if (Locale.getDefault().getLanguage().equals("fr")){
            welcomeLbl.setText(rb.getString("Welcome")); 
            passwordLbl.setText(rb.getString("Password")); 
            loginBtn.setText(rb.getString("Login"));
            usernameLbl.setText(rb.getString("Username")); 
            timeLbl.setText(rb.getString("Time"));
            langLocLbl.setText(rb.getString("Language"));
            loginError = rb.getString("LoginErr");
        }
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm"); 
        ZoneId usersZid = ZoneId.systemDefault();
        LocalTime usersTime = LocalTime.now();  
        
        loginLocationLbl.setText(usersZid.toString());
        loginTimeLbl.setText(usersTime.format(dtf));
    }    

    /** * Will pass the UserID to other screens. UserID is needed to create appointments and will default to the current user ID
     * @return */ 
    public static Integer passUserID(){
        return userID; 
    } 
}


    
