/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduleprogram;

import daouser.UserImp;
import java.io.IOException;
import java.sql.Connection;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import utils.DBConnection;


/**
 *
 * @author katil
 * This is the main application. 
 */
public class ScheduleProgram extends Application {

    
    /** * Starts the initial program and launches the login screen.Checks the default Locale and sets the language appropriately
     * @param stage
     * @throws java.lang.Exception*/
   @Override
    public void start(Stage stage) throws Exception {
        Parent root; 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login_Menu.fxml"));
        
         if (Locale.getDefault().getLanguage().equals("fr")){ 
        ResourceBundle rb = ResourceBundle.getBundle("lang_files/lang", Locale.getDefault());
        loader.setResources(rb);
        }
        
        root=loader.load();
        Scene scene = new Scene(root); 
        stage.setScene(scene);
        stage.show();
 
        //ResourceBundle rb = ResourceBundle.getBundle("lang_files/lang", Locale.getDefault());
        
       

        
         
        

       
    }
    
    
    /**Main application. 
     * @param args*/
    public static void main(String[] args) {
                DBConnection.startConnection();
                Connection conn = DBConnection.getConnection();
        // starts the init method which starts the GUI 
                 launch(args);
        

        
      
        
     

        
    }
    
}
