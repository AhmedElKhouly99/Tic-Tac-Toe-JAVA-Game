/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import player.AllPlayers;
import SocketHandler.PlayerSocket;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class ScoreListController extends Thread implements Initializable {

    @FXML
    private TableView<AllPlayers> table_users;
    @FXML
    private TableColumn<AllPlayers, String> col_name;
    @FXML
    private TableColumn<AllPlayers, Integer> col_score;
    @FXML
    private TableColumn<AllPlayers, Integer> col_rank;

    @FXML
    private Button BackBtn;

    ObservableList<AllPlayers> listM;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        start();
    }

    @FXML
    void GoBackToMainMenu(ActionEvent event) throws IOException {
        stop();
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        Stage window = (Stage) BackBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    @Override
    public void run() {
        while (true) {
//            try {
//                PlayerSocket.outObj.writeObject("rankings");
//                Object res = (Object) PlayerSocket.inObj.readObject();
                PlayerSocket.sendMsg("rankings");
                Object res = PlayerSocket.receiveMsg();
                String garbage = "hhh";
                if(res != null){
                if (res.getClass() == garbage.getClass()) {
                    continue;
                }
                
                List<AllPlayers> list = (List<AllPlayers>) res;

                listM = FXCollections.observableArrayList(list);

//            } catch (ClassNotFoundException ex) {
//                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException ex) {
//
//                Logger.getLogger(ScoreListController.class.getName()).log(Level.SEVERE, null, ex);
//            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    col_rank.setCellValueFactory(new PropertyValueFactory<AllPlayers, Integer>("rank"));
                    col_name.setCellValueFactory(new PropertyValueFactory<AllPlayers, String>("username"));
                    col_score.setCellValueFactory(new PropertyValueFactory<AllPlayers, Integer>("score"));
                    table_users.setItems(listM);
                }
            });}else{ 
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Parent root = null;
                            try {
                                root = FXMLLoader.load(getClass().getResource("Main.fxml"));
                            } catch (IOException ex) {
                                Logger.getLogger(ScoreListController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            Stage window = (Stage) BackBtn.getScene().getWindow();;
                            window.setScene(new Scene(root));
                        }
                    });
                    
                    break;
                }
            try {
                sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(ScoreListController.class.getName()).log(Level.SEVERE, null, ex);
            }
//            } catch (InterruptedException ex) {
//                Logger.getLogger(ScoreListController.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
    }
}
