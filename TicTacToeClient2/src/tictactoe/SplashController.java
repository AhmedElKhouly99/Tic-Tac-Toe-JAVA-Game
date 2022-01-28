/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class SplashController implements Initializable {

    @FXML
    private Circle cir1;
    @FXML
    private Circle cir2;
    @FXML
    private Circle cir3;
    @FXML
    private Button StartBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setRotate(cir1, true, 360, 10);
        setRotate(cir2, true, 180, 18);
        setRotate(cir3, true, 145, 24);
    }    
    int rotate = 0;
    @FXML
    private void play(ActionEvent event) {
        try {
            MyStartBtn();
        } catch (IOException ex) {
            Logger.getLogger(SplashController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    private void setRotate(Circle c, boolean reverse, int angle, int duration){
        RotateTransition rt = new RotateTransition(Duration.seconds(duration), c);
        rt.setAutoReverse(reverse);
        rt.setByAngle(angle);
        rt.setDelay(Duration.seconds(0));
        rt.setRate(3);
        rt.setCycleCount(10);
        rt.play();
    }
    public void MyStartBtn() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Stage window = (Stage) StartBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }
}
