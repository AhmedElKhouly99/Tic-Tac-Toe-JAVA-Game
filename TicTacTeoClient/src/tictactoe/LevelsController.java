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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class LevelsController implements Initializable {

    @FXML
    private AnchorPane EasyPane;
    @FXML
    private Button EasyBtn;
    @FXML
    private AnchorPane HardPane;
    @FXML
    private Button HardBtn;
    @FXML
    private Button BackToMainBtn;
    
    static boolean hardAI=false;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void GoToEasyGame(ActionEvent event) throws IOException {
         
        hardAI=false;    
        Parent root = FXMLLoader.load(getClass().getResource("VsComputerMode.fxml"));
        Stage window = (Stage) EasyBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    @FXML
    private void GoToHardGame(ActionEvent event) throws IOException {
        
        hardAI=true;
        Parent root = FXMLLoader.load(getClass().getResource("VsComputerMode.fxml"));
        Stage window = (Stage) HardBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    @FXML
    private void BackToMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Stage window = (Stage) BackToMainBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }
    
}
