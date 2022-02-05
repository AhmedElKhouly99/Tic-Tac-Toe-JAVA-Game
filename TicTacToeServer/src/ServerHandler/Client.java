/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerHandler;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 *
 * @author Abanoub Kamal
 */
public class Client extends Application{
    String password;
    String username;
    
    
    Label statusLabel;
    Label statusField;
    FlowPane serverStatusPane;
    
    Button refreshBtn;
    FlowPane flowPane;  
    
    Label unamelabel;
    TextField textfield1;    
    Label passlabel;
    TextField textfield2;
    FlowPane signinpane;
    
    Button SigninBtn;
    FlowPane btnpane;
    
    BorderPane rootPane;  
    Scene myScene;

    Socket clientSocket;
    DataInputStream inS;
    PrintStream outS;
    String serverStatus = null;
    
    @Override
    public void init(){
        
        statusLabel = new Label("Server Status : ");
        statusField = new Label();
        serverStatusPane = new FlowPane(statusLabel, statusField);
        
        unamelabel =new Label("User Name");
        textfield1 = new  TextField();
        FlowPane usernamePane = new FlowPane(unamelabel, textfield1);
        
        passlabel =new Label("Password");
        textfield2 = new  TextField();
        FlowPane passwordPane = new FlowPane(passlabel, textfield2);
        
        SigninBtn = new Button("Sign in");

        signinpane =new FlowPane(Orientation.VERTICAL, usernamePane, passwordPane, SigninBtn);
        
        
        refreshBtn = new Button("Refresh");
        refreshBtn.setTranslateX(3);
        refreshBtn.setTranslateY(-3);
        flowPane = new FlowPane(refreshBtn);
        
        rootPane = new BorderPane();
        rootPane.setTop(serverStatusPane);
        rootPane.setBottom(flowPane);
        rootPane.setCenter(signinpane);
        
        
        myScene = new Scene(rootPane, 375, 400);

        try{
            clientSocket = new Socket("192.168.133.1", 5005);
            inS = new DataInputStream(clientSocket.getInputStream());
            outS = new PrintStream(clientSocket.getOutputStream());
        }catch(Exception e){
            
            System.out.println("clientapp.ClientApp.init().init client socket and streams");
        }

    }

    @Override
    public void start(Stage primaryStage) {

        SigninBtn.setOnAction(e -> {
            //Retrieving data
             username = textfield1.getText();
             password = textfield2.getText();
             outS.println(username);
             outS.println(password);
          
            });
        
        primaryStage.setTitle("Chat Client1");
        primaryStage.setScene(myScene);
        primaryStage.show();
        
        startThreadToUpdateClientGui();
    }
    
    /* Actions taken when the client app closed */
    @Override
    public void stop() throws IOException
    {
        if(serverStatus != null) // at the client is not connected yet don't execute this block of code
        {
            /* close the input and output streams of the client */
            inS.close();
            outS.close();
            /* close the socket of the client */
            clientSocket.close();
        }
        /* close the thread resposible for make changes on the GUI */
        endThreadThatUpdateClientGui();
        /* terminate the  JavaFX application explicitly */
        Platform.exit();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
    
    
    /**************************************************************************/
    /**************** thread to renew the data of client screen ***************/
    Thread updatingClientGuiThread;
    private void startThreadToUpdateClientGui()
    {
            Runnable runnable = new Runnable(){
            @Override
            public void run(){
    
                while(true){
                    try{
//                        outS.println("Online"); // for client status // give an exception error if there is no server
//                        serverStatus = inS.readLine(); // for server status // give an exception error if there is no server
                        
                        Platform.runLater(new Runnable(){
                        @Override
                            public void run(){
                                statusField.setText(serverStatus);  // write the serverStatus on client GUI  
                                            
                            }
                        }); 
                    }catch(Exception e){
                        e.getStackTrace();
                        System.out.println("TicTacToe.run().threadUpdateGui");
                        /* in case the server isn't exist */
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run(){
                                statusField.setText("Ofline!");    
                            }

                        }); 
                    }
                    
                    try {
                        updatingClientGuiThread.sleep(50);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        updatingClientGuiThread = new Thread(runnable);
        updatingClientGuiThread.start();
    }
    
    private void endThreadThatUpdateClientGui()
    {
        updatingClientGuiThread.stop();
    }
    
    private static void refreshApplication()
    {
//        primaryStage.close();
//        Platform.runLater( () -> new ReloadApp().start( new Stage() ) );  
//        Platform.exit();
//        Application.launch();
    }
    
}