/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class MenuController implements Initializable {

    @FXML
    private Button RankBtn;
    
    @FXML
    private Button StartGameBtn;

      @FXML
    private Button LogoutBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void GoToRanks(ActionEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("ScoreList.fxml"));
        Stage window = (Stage) RankBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }
    
    @FXML
    void StartGame(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MultiPlayerMode.fxml"));
        Stage window = (Stage) StartGameBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }
    
       @FXML
    void Logout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Stage window = (Stage) LogoutBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }
}
