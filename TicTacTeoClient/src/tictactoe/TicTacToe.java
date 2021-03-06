/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;


import SocketHandler.PlayerSocket;
import game.Game;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static tictactoe.MultiPlayerModeController.gameTh;

/**
 *
 * @author Admin
 */
public class TicTacToe extends Application {
    
    @Override 
    public void init()
    {
       
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("splash.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        primaryStage.setOnCloseRequest((event) -> {
            if(MultiPlayerModeController.gameTh){
                PlayerSocket.sendMsg("exited");
                Game.myGame.setUsername1_x(null);
            }
            Platform.exit();
            System.exit(0);
            if(PlayerSocket.inObj != null)
                PlayerSocket.closeSoket();
        });

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
