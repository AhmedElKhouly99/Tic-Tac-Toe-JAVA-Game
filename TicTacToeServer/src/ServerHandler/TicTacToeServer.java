/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic.tac.toe_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import static tic.tac.toe_server.ClientHandler.clientsVector;

/**
 *
 * @author ahmed
 */
public class TicTacToeServer extends Application {
    
    Label clientNum;
    Label numField; // using Lable here as i could set the text of it and give it initial value(0) to show
    Label clients;
    FlowPane clientsField; // using FlowPane here not Label as i can't appent text on label
     
    Button refreshBtn;
    Button closeBtn;
    
    FlowPane lapelPane;
    FlowPane textFieldPane;
    FlowPane buttonsPane;
    BorderPane rootPane;
     
    Scene myScene;
    
    ServerSocket myServerSocket;

    Vector<ClientHandler> clietnsVector;
    
    @Override
    public void init()
    {
        /**********************************************************************/
        /*************************** the server GUI ***************************/
        clientNum = new Label("Client Num: ");
        clientNum.setPrefWidth(100);
        numField = new Label("0");
        
        clients = new Label("Online Clients: ");
        clients.setPadding(new Insets(0, 0, 0,10));
        clientsField = new FlowPane(Orientation.VERTICAL);
        clientsField.setPadding(new Insets(0,0,0,10)); // to make it aligne the word "Online Clients"
       
        refreshBtn = new Button("Refresh");
        closeBtn = new Button("Close");
        
        lapelPane = new FlowPane( clientNum, numField);
        lapelPane.setVgap(20);
        lapelPane.setPadding(new Insets(10, 0, 10, 10));
        
        buttonsPane = new FlowPane(refreshBtn, closeBtn);
        buttonsPane.setHgap(223);
        
        rootPane = new BorderPane();
        rootPane.setTop(lapelPane);
        rootPane.setLeft(clients);
        rootPane.setCenter(clientsField);
        rootPane.setBottom(buttonsPane);

        myScene = new Scene(rootPane, 350, 350);
        
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        /* close button action */
        closeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    actionAtServerAppClose();
                } catch (IOException ex) {
                    Logger.getLogger(TicTacToeServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
//        refreshBtn.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                        
//            }
//        });

        /* show the app */
        primaryStage.setTitle("TicTacToeServer!");
        primaryStage.setScene(myScene);
        primaryStage.show();
             
        /* add the recovery code here */
        
        
        StartThreadToAcceptClients();
        
        startThreedToUpdateServerGui();
    }
    
    /* Actions taken when the server app closed */
    @Override
    public void stop() throws IOException
    {
        actionAtServerAppClose();
    }
    
    
    /**************************************************************************/
    /******************************* The main *********************************/
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
    
    
    
    
    /**************************************************************************/
    /***************** thread for the server to accept clients ****************/
    Thread acceptClientsThread;
    private void StartThreadToAcceptClients()
    {
        Runnable runnable = new Runnable(){
            @Override
            public void run(){
                
                try{
                    myServerSocket = new ServerSocket(5000);
                    while(true){
                        Socket internalSocket = myServerSocket.accept();
                        
                        new ClientHandler(internalSocket);
                    }    
                }catch(Exception e){
                    System.out.println("TicTacToeServer.start<in the thread>()");
                }   
            }
        };
        
        acceptClientsThread = new Thread(runnable);
        acceptClientsThread.start();
    }
    
    private void endThreadThatAcceptClients()
    {
        acceptClientsThread.stop();
    }
    
    
    /**************************************************************************/
    /**************** thread to renew the data of server screen ***************/
    int lastSize = 1;
    Thread updateServerGuiThread;
    private void startThreedToUpdateServerGui(){
        Runnable task = new Runnable() {
            public void run(){
                runTask();
            }
        };
        updateServerGuiThread = new Thread(task);
        updateServerGuiThread.start();
    }
    
    private void runTask(){     
        while(true)
        { 
            try
            {
                /* get the online clients */
                clietnsVector = ClientHandler.getClientsVector();
                Platform.runLater( new Runnable(){
                    public void run(){
                        numField.setText(new Integer(clietnsVector.size()).toString());
                        if(lastSize != clietnsVector.size())
                        {
                            lastSize = clietnsVector.size();
                            clientsField.getChildren().removeAll(clientsField.getChildren());
                            for(ClientHandler ch: clietnsVector){                          
                                clientsField.getChildren().add(new Label(ch.getId()+"\n"));
                          }
                        }
                    }
                });
                
                updateServerGuiThread.sleep(50);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    
    private void endThreadThatUpdateServerGui()
    {
        updateServerGuiThread.stop();
    }
    
    /**************************************************************************/
    /******************* action should take at close the server ***************/
    private void actionAtServerAppClose() throws IOException
    {
        clietnsVector = ClientHandler.getClientsVector();
        
        /* end all internal sockets (threads that stands against) */
        for(ClientHandler ch: clientsVector)
        {
            try {
                ch.currentThread().wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(TicTacToeServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        /* close the thread resposible for accept clients */
        endThreadThatAcceptClients();
        /* close the socket of the server */
        myServerSocket.close();
        /* close the thread resposible for make changes on the GUI */
        endThreadThatUpdateServerGui();
        /* terminate the  JavaFX application explicitly */
        Platform.exit();
    }
    
}
