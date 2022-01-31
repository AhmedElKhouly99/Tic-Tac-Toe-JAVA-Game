
 
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
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author shorook
 */
public class MultiPlayerModeController implements Initializable {

    @FXML
    private Button btn1;
    @FXML
    private Button btn2;
    @FXML
    private Button btn3;
    @FXML
    private Button btn4;
    @FXML
    private Button btn5;
    @FXML
    private Button btn6;
    @FXML
    private Button btn7;
    @FXML
    private Button btn8;
    @FXML
    private Button btn9;
    @FXML
    private Button btnplayagain;
    @FXML
    private Label playerOneName;
    @FXML
    private Label playerTwoName;
    @FXML
    private Label playerTwoScore;
    @FXML
    private Label playerOneScore;
    @FXML
    private Button returnBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void backToMainPage(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Stage window = (Stage) returnBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    @FXML
    private void btnPressed(ActionEvent event) {
    }

    @FXML
    private void replayAgain(ActionEvent event) {
    }
    
}

