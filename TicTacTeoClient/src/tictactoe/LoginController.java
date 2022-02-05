/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import player.Players;
import SocketHandler.PlayerSocket;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;

import javafx.scene.control.PasswordField;

import javafx.scene.control.TextField;
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

    @FXML
    private TextField unameField;

    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Button BackToMainBtn;

    
    static Vector<Players> playersVector = new Vector<Players>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    @FXML
    void BackToMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Stage window = (Stage) BackToMainBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    @FXML
    private void GoToRegister(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Register.fxml"));
        Stage window = (Stage) GoToRegisterBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    @FXML
    private void goToGame(ActionEvent event) throws IOException, ClassNotFoundException {

        if(unameField.getText().equals("") || passwordField.getText().equals("") ){
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("please enter your username and password");
            alert.show();
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialog");
        }else{
            PlayerSocket.socketInit();
            PlayerSocket.outObj.writeObject("login::"+unameField.getText()+"::"+passwordField.getText());
            String respond = (String)PlayerSocket.inObj.readObject();
            if("login::done".equals(respond)){
                Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
                Stage window = (Stage) loginBtn.getScene().getWindow();
                window.setScene(new Scene(root));
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Incorrect username or password");
                alert.show();
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
                dialogPane.getStyleClass().add("myDialog");
            
                PlayerSocket.closeSoket();
            }

    
       
//        PlayerSocket.socketInit();
        //////////////////////////////////////////////
//        try {
//            PlayerSocket.outObj.writeObject("onlinePlayers");
//            playersVector.removeAllElements();
//            playersVector = (Vector<Player>)PlayerSocket.inObj.readObject();
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
//        }

        }

    } 
}
