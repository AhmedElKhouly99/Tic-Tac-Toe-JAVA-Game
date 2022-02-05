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
 * @author shorook
 */
public class MainController implements Initializable {

    @FXML
    private Button MultiPlayerBtn;
    @FXML
    private Button singlePlayerBtn;
        @FXML
    private Button ExitBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void MultiPlayer(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Stage window = (Stage) MultiPlayerBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    @FXML
    private void singleplayer(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("VsComputerMode.fxml"));
        Stage window = (Stage) singlePlayerBtn.getScene().getWindow();
        window.setScene(new Scene(root));
        
    }
    
      @FXML
    void Exit(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("splash.fxml"));
        Stage window = (Stage) ExitBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }
}
