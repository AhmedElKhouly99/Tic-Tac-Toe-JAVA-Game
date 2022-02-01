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
public class LoginController implements Initializable {

    @FXML
    private Button GoToRegisterBtn;
    @FXML
    private Button loginBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void GoToRegister(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Register.fxml"));
        Stage window = (Stage) GoToRegisterBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    @FXML
    private void goToGame(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MultiPlayersMode.fxml"));
        Stage window = (Stage) GoToRegisterBtn.getScene().getWindow();
        window.setScene(new Scene(root)); 
    }
    
    
    
}