/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import daoappts.ApptImp;
import daouser.UserImp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class for the Type and Month Report. 
 * This will display in table view a list of appointments by Type
 *
 * @author katil
 */
public class TypeAndMonthController implements Initializable {
    
    private ObservableList<ObservableList>typeByMonth = FXCollections.observableArrayList();
    Stage stage; 
    Parent scene;
    
    @FXML
    private TableView typeMonthTbl;{

    }
    
    @FXML
    void onActionBack(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Report_Menu.fxml")); 
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Initializes the controller class for the type and month report. 
     * This will iterate through the result set and create a dynamic table based on the result set size
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       ResultSet rSet = ApptImp.getApptTypeCount(); 
       // creates the columns 
       try  {
       for (int i = 0; i < rSet.getMetaData().getColumnCount(); i++){
           final int j = i; 
           TableColumn col = new TableColumn(rSet.getMetaData().getColumnName(i+1));
            col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>(){
                public ObservableValue<String>call(CellDataFeatures<ObservableList, String> param){
                    return new SimpleStringProperty(param.getValue().get(j).toString()); 
                }
            });     
           typeMonthTbl.getColumns().addAll(col);   
        }
       
       while (rSet.next()){
           ObservableList<String> data = FXCollections.observableArrayList(); 
           for (int i = 1; i <= rSet.getMetaData().getColumnCount(); i++){
               data.add(rSet.getString(i));
           }
           typeByMonth.add(data); 
       }
       typeMonthTbl.setItems(typeByMonth);
       }catch(SQLException ex) {
        Logger.getLogger(UserImp.class.getName()).log(Level.SEVERE, null, ex);
       }
  
    }    
    
}
