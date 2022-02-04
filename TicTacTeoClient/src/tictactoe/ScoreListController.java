/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import SocketHandler.AllPlayers;
import SocketHandler.Player;
import SocketHandler.PlayerSocket;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import static tictactoe.LoginController.playersVector;


public class ScoreListController implements Initializable {

    @FXML
    private TableView<AllPlayers> table_users;
    @FXML
    private TableColumn<AllPlayers, String> col_name;
    @FXML
    private TableColumn<AllPlayers, Integer> col_score;

    ObservableList<AllPlayers> listM;
    int index = -1;
    
    
    ObservableList<AllPlayers> list;
    
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
//        col_name.setCellValueFactory(new PropertyValueFactory<AllPlayers,String>("username"));
//        col_score.setCellValueFactory(new PropertyValueFactory<AllPlayers,Integer>("score"));
//        table_users.setItems(listM);
        //listM = mysqlconnect.getDatausers();
        getAllUsers();
    }    
    
    
    public ObservableList<AllPlayers> getAllUsers()
    {
        ObservableList<AllPlayers> list = FXCollections.observableArrayList();
        Thread allUsersTh = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {                    
                    try {
                        PlayerSocket.outS.println("rankings");
                        playersVector.removeAllElements();
                        listM = (ObservableList<AllPlayers>) PlayerSocket.inObj.readObject();
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(ScoreListController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            col_name.setCellValueFactory(new PropertyValueFactory<AllPlayers,String>("username"));
                            col_score.setCellValueFactory(new PropertyValueFactory<AllPlayers,Integer>("score"));
                            table_users.refresh();
                            table_users.setItems(listM);
                        }
                    });
                }
            }
        });
        allUsersTh.start();
        
        
        return list;
    }
}
