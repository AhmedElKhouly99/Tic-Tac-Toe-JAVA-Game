/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

/**
 * FXML Controller class
 *
 * @author Abanoub Kamal
 */
public class MainController implements Initializable {

    private int lastNumberOfLoginPlayers;
    private boolean startStatusFlag;
//    private boolean stopStatusFlag;
    private int SERVER_SOCKET_PORT;    
    
    static ServerSocket myServerSocket;
    static Vector<MyClientHandler> clietnsVector;
    
    private Button startBtn;
    private Button stopBtn;
    private FlowPane listOfOnlinePlayers;
    @FXML
    private Label status;
    @FXML
    private Button activateBtn;
    @FXML
    private Tab onlinePlayersTab;
    @FXML
    private Tab offlinePlayersTab;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        SERVER_SOCKET_PORT = 5005;
        lastNumberOfLoginPlayers = 0;
        startStatusFlag = false;
//        stopStatusFlag = false;
    } 
    
    
    @FXML
    private void handleActivateAndDeactivateAction(ActionEvent event) {
        if(event.getSource() == activateBtn && startStatusFlag == false)
        {
            startStatusFlag = true;
            StartThreadToAcceptClients();
            startThreedToUpdateServerGui();
            
            /* change the button image to deactive next time (execute in UpdateThread)*/ 
            
            
            System.out.println("ServerHandler.MainController.handleStartButtonAction()");
        }else{
            startStatusFlag = false;
            try {
                actionAtServerAppClose();
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("ServerHandler.MainController.handleActivateAndDeactivateAction().IOException");
            }
            
            /* change the button image to active next time */ 
            
            
            System.out.println("ServerHandler.MainController.handleStopButtonAction()");
        }
        
    }

    @FXML
    private void displayOnlinePlayers(Event event) {
        
        
    }

    @FXML
    private void displayOfflinePlayers(Event event) {
        
        
    }
    
    
    
    

//    private void handleStartButtonAction(ActionEvent event) {
//        if(event.getSource() == startBtn && startStatusFlag == false)
//        {
//            startStatusFlag = true;
//            stopStatusFlag = false;
//            StartThreadToAcceptClients();
//            startThreedToUpdateServerGui();
//            System.out.println("ServerHandler.MainController.handleStartButtonAction()");
//        }
//    }
//
//    private void handleStopButtonAction(ActionEvent event) throws IOException {
//        if(event.getSource() == stopBtn && stopStatusFlag == false)
//        {
//            startStatusFlag = false;
//            stopStatusFlag = true;
//            actionAtServerAppClose();
//            System.out.println("ServerHandler.MainController.handleStopButtonAction()");
//        }
//    }
    
    
    
    /**************************************************************************/
    /***************** thread for the server to accept clients ****************/
    private static Thread acceptClientsThread;
    private void StartThreadToAcceptClients()
    {
        Runnable runnable = new Runnable(){
            @Override
            public void run(){
                
                try{
                    myServerSocket = new ServerSocket(SERVER_SOCKET_PORT);
                    while(true){
                        Socket internalSocket = myServerSocket.accept();
                        
                        new MyClientHandler(internalSocket);
//                        System.out.println("A player accepted");
                    }    
                }catch(Exception e){
                    System.out.println("ServerHandler.MainController.StartThreadToAcceptClients().Exception");
                }   
            }
        };
        
        acceptClientsThread = new Thread(runnable);
        acceptClientsThread.start();
    }
    
    private static void endThreadThatAcceptClients()
    {
        acceptClientsThread.stop();
    }
    
    
    /**************************************************************************/
    /**************** thread to renew the data of server screen ***************/
    private static Thread updateServerGuiThread;
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
                /* get the online players */
                clietnsVector = MyClientHandler.getClientsVector();
                /* get the ofline players */
                                ///////////// \\\\\\\\\\\\\
                Platform.runLater( new Runnable(){
                    public void run(){ 
                        /* change the button image to active next time */
                        
                        /* change the button image to deactive next time */
                        
                        
                        /* for online players */
                        if(lastNumberOfLoginPlayers != clietnsVector.size())
                        {
                            lastNumberOfLoginPlayers = clietnsVector.size();
                            listOfOnlinePlayers.getChildren().removeAll(listOfOnlinePlayers.getChildren());
                            for(MyClientHandler ch: clietnsVector){                          
                                listOfOnlinePlayers.getChildren().add(new Label(ch.thisUname + " score = " + ch.score));
                            }
                        }
                        /* for ofline players */
//                        if(lastNumberOfLoginPlayers != clietnsVector.size())
//                        {
//                            lastNumberOfLoginPlayers = clietnsVector.size();
//                            listOfOnlinePlayers.getChildren().removeAll(listOfOnlinePlayers.getChildren());
//                            for(MyClientHandler ch: clietnsVector){                          
//                                listOfOnlinePlayers.getChildren().add(new Label(ch.thisUname + " score = " + ch.score));
//                            }
//                        }
                    }
                });
                
                updateServerGuiThread.sleep(50);
            }catch(InterruptedException e){
                System.out.println("ServerHandler.MainController.runTask().InterruptedException");
            }
        }
    }
    
    private static void endThreadThatUpdateServerGui()
    {
        updateServerGuiThread.stop();
    }
    
    /**************************************************************************/
    /******************* action should take at close the server ***************/
    static void actionAtServerAppClose() throws IOException
    {
        clietnsVector = MyClientHandler.getClientsVector();
        
        /* end all internal sockets (threads that stands against) */
        for(MyClientHandler ch: clietnsVector)
        {
            try {
                ch.currentThread().wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MyTicTacToeServer.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("ServerHandler.MainController.actionAtServerAppClose().InterruptedException");
            }
        }
        /* close the thread resposible for accept clients */
        endThreadThatAcceptClients();
        /* close the socket of the server */
        myServerSocket.close();
        /* close the thread resposible for make changes on the GUI */
        endThreadThatUpdateServerGui();
    }

    

    
}
