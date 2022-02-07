/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerHandler;

import Database.Database;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ahmed
 */
public class MyTicTacToeServer extends Application {
    
    @Override
    public void init()
    {
        new Database();        
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
       
        /* show the app */
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene myScene = new Scene(root);
        primaryStage.setTitle("TicTacToe Server!");
        primaryStage.setScene(myScene);
        primaryStage.show();

    }
    
    /* Actions taken when the server app closed */
    @Override
    public void stop() throws IOException
    {
        /* close all threads opened by the server */
        if (MainController.startStatusFlag == true && MainController.stopStatusFlag== false)
            MainController.actionAtServerAppClose();
        /* terminate the  JavaFX application explicitly */
        Platform.exit();
    }
    
    
    /**************************************************************************/
    /******************************* The main *********************************/
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
    
}
