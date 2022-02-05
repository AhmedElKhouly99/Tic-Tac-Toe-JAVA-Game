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

public class RegisterController implements Initializable {
    
    @FXML
    private Button GoToLoginBtn;
     @FXML
    private TextField unameRegField;

    @FXML
    private PasswordField PassRegField;

    @FXML
    private Button SignUpBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void GoToLogin(ActionEvent event) throws IOException {
       
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Stage window = (Stage) GoToLoginBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }
    
    @FXML
    void GoToMenu(ActionEvent event) throws IOException {

         
        if(unameRegField.getText().equals("") || PassRegField.getText().equals("") ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("please enter your username and password");
            alert.show();
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialog");
            
            
        }else if(!(unameRegField.getText().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"))){
            Alert alertUsername = new Alert(Alert.AlertType.ERROR);
            alertUsername.setContentText("Username must be: \n - At least 8 chars \n - Contains at least one digit \n - Contains at least one lower alpha char and one upper alpha char \n - Contains at least one char within a set of special chars (@#%$^ etc.)\n - Does not contain space, tab, etc.");
            alertUsername.show();
            DialogPane dialogPane = alertUsername.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialog");
            
            
        }else if(!(PassRegField.getText().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"))){
            Alert alertPass = new Alert(Alert.AlertType.ERROR);
            alertPass.setContentText("Password must be:\n - At least 8 chars \n - Contains at least one digit\n - Contains at least one lower alpha char and one upper alpha char\n - Contains at least one char within a set of special chars (@#%$^ etc.)\n - Does not contain space, tab, etc.");
            alertPass.show();
            DialogPane dialogPane = alertPass.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialog");
        }
        else{
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Stage window = (Stage) SignUpBtn.getScene().getWindow();
        window.setScene(new Scene(root));
        }
    }
    
}
