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

//    public static LoginController lg = new LoginController();
    @FXML
    private Button GoToRegisterBtn;
    @FXML
    private Button loginBtn;

    @FXML
    private TextField unameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    public static Button BackToMainBtn;

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
        Stage window = (Stage) GoToRegisterBtn.getScene().getWindow();
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

        String uname = unameField.getText();
        String pass = passwordField.getText();
        if (uname.equals("") || pass.equals("")) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("please enter your username and password");
            alert.show();
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialog");

        } else {
            PlayerSocket.socketInit();
//            PlayerSocket.outObj.writeObject("login::" + unameField.getText() + "::" + passwordField.getText());
//            String respond = (String) PlayerSocket.inObj.readObject();
            PlayerSocket.sendMsg((String)"login::" + unameField.getText() + "::" + passwordField.getText());
            String respond = (String)PlayerSocket.receiveMsg();
            if ("login::done".equals(respond)) {
                Players.myPlayer = new Players(uname, 0);
                Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
                Stage window = (Stage) loginBtn.getScene().getWindow();
                window.setScene(new Scene(root));
            } else if ("login::exist".equals(respond)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("User logged in from other device!");
                alert.show();
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
                dialogPane.getStyleClass().add("myDialog");
                PlayerSocket.closeSoket();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Incorrect username or password");
                alert.show();
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
                dialogPane.getStyleClass().add("myDialog");

                PlayerSocket.closeSoket();
            }

        }

    }
}
