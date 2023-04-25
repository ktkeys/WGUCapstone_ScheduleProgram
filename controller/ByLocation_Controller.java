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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class for the Appointments by location report
 *
 * @author katil
 */


public class ByLocation_Controller implements Initializable {
    
    Stage stage; 
    Parent scene;
    private ObservableList<ObservableList>typeByLoc = FXCollections.observableArrayList();
    
    @FXML
    private TableView typeMonthTbl;
    
    @FXML
    void onActionBack(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Report_Menu.fxml")); 
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Initializes the controller class for viewing the report for Type of Appointments by Location.
     * This will initialize and set the table based off the number of columns in the result set
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

       ResultSet rSet = ApptImp.getTypeLocation(); 
       // creates the columns and the column headers
       try  {
       for (int i = 0; i < rSet.getMetaData().getColumnCount(); i++){
           final int j = i; 
           TableColumn col = new TableColumn(rSet.getMetaData().getColumnName(i+1));
           col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>(){
                public ObservableValue<String>call(TableColumn.CellDataFeatures<ObservableList, String> param){
                    return new SimpleStringProperty(param.getValue().get(j).toString()); 
                }
            });     
           typeMonthTbl.getColumns().addAll(col);     
       }
       
       //adds the data 
       while (rSet.next()){
           ObservableList<String> data = FXCollections.observableArrayList(); 
           for (int i = 1; i <= rSet.getMetaData().getColumnCount(); i++){
               data.add(rSet.getString(i));
           }
           typeByLoc.add(data); 
       }
       typeMonthTbl.setItems(typeByLoc);
       }catch(SQLException ex) {
        Logger.getLogger(UserImp.class.getName()).log(Level.SEVERE, null, ex);
       }    
    }    
}    
    

