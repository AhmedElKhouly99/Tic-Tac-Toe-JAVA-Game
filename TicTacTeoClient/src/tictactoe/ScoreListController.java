/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*package tictactoe;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class ScoreListController implements Initializable {

    @FXML
    private TableView<users> table_users;
    @FXML
    private TableColumn<users, String> col_name;
    @FXML
    private TableColumn<users, Integer> col_score;

    ObservableList<users> listM;
    int index = -1;
    
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        col_name.setCellValueFactory(new PropertyValueFactory<users,String>("name"));
        col_score.setCellValueFactory(new PropertyValueFactory<users,Integer>("score"));
        listM = mysqlconnect.getDatausers();
        table_users.setItems(listM);
    }    
    
}
*/