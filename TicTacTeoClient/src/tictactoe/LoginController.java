/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import SocketHandler.Player;
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
import javafx.scene.control.Button;

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
    
    static Vector<Player> playersVector = new Vector<Player>();

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
        PlayerSocket.socketInit();
        //////////////////////////////////////////////
//        try {
//            PlayerSocket.outS.println("onlinePlayers");
//            playersVector.removeAllElements();
//            playersVector = (Vector<Player>)PlayerSocket.inObj.readObject();
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        /////////////////////////////////////////////////
        String message=new String();
       
        message="login::"+unameField.getText()+"::"+passwordField.getText();
        
        PlayerSocket.outS.println(message);
        
        String respond=PlayerSocket.inS.readLine();
        
        if("login::done".equals(respond))
        {
//            PlayerSocket.outS.println("invite::khouly");
//            if("inviteAccepted".equals(PlayerSocket.inS.readLine()))
//            {
//                Parent root = FXMLLoader.load(getClass().getResource("MultiPlayersMode.fxml"));
//                Stage window = (Stage) GoToRegisterBtn.getScene().getWindow();
//                window.setScene(new Scene(root));
//            }
            
            Parent root = FXMLLoader.load(getClass().getResource("ScoreList.fxml"));
            Stage window = (Stage) GoToRegisterBtn.getScene().getWindow();
            window.setScene(new Scene(root));
            
        }
        else
        {
            
        }
         
    }
    
    
    
}
