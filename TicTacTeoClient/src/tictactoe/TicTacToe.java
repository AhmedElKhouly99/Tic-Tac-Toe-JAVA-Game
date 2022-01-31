/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import SocketHandler.PlayerSocket;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Admin
 */
public class TicTacToe extends Application {
    
    @Override 
    public void init()
    {
<<<<<<< HEAD
        PlayerSocket.socketInit();
=======
      //PlayerSocket.socketInit();  
>>>>>>> d2875746ea91d91e63a94e23c201f77d2e5025fa
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
<<<<<<< HEAD
          
=======
        
>>>>>>> d2875746ea91d91e63a94e23c201f77d2e5025fa
        Parent root = FXMLLoader.load(getClass().getResource("splash.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
